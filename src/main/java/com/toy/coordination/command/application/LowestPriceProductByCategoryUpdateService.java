package com.toy.coordination.command.application;

import com.toy.brand.command.domain.Brand;
import com.toy.brand.command.domain.BrandRepository;
import com.toy.common.exception.DomainException;
import com.toy.coordination.command.domain.LowestPriceProductByCategory;
import com.toy.coordination.command.domain.LowestPriceProductByCategoryRepository;
import com.toy.outbox.command.application.OutboxService;
import com.toy.outbox.command.domain.event.BrandDeletedOutboxEvent;
import com.toy.outbox.command.domain.event.ProductDeletedOutboxEvent;
import com.toy.outbox.command.domain.event.ProductPriceChangedOutboxEvent;
import com.toy.outbox.command.domain.event.ProductRegisteredOutboxEvent;
import com.toy.product.command.domain.Category;
import com.toy.product.command.domain.Product;
import com.toy.product.command.domain.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

import static com.toy.common.exception.ExceptionCode.BRAND_NOT_EXIST;
import static com.toy.common.exception.ExceptionCode.RETRY_FAILED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Slf4j
@Service
public class LowestPriceProductByCategoryUpdateService {
    private final LowestPriceProductByCategoryRepository repository;
    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;
    private final OutboxService outboxService;

    @Autowired
    public LowestPriceProductByCategoryUpdateService(
            LowestPriceProductByCategoryRepository repository,
            BrandRepository brandRepository,
            ProductRepository productRepository,
            OutboxService outboxService
    ) {
        this.repository = repository;
        this.brandRepository = brandRepository;
        this.productRepository = productRepository;
        this.outboxService = outboxService;
    }

    @Retryable(retryFor = OptimisticLockingFailureException.class, backoff = @Backoff(delay = 300), maxAttempts = 5)
    @Transactional(propagation = REQUIRES_NEW)
    @TransactionalEventListener
    public void productRegisteredOutboxEventHandler(ProductRegisteredOutboxEvent outboxEvent) {
        LowestPriceProductByCategory before = repository.findByCategory(outboxEvent.getCategory());

        Brand brand = brandRepository.findById(outboxEvent.getBrandId())
                .orElseThrow(() -> new DomainException(BRAND_NOT_EXIST));

        LowestPriceProductByCategory after = new LowestPriceProductByCategory(
                outboxEvent.getAggregateRootId(),
                outboxEvent.getCategory(),
                brand.name(),
                outboxEvent.getPrice()
        );

        if (before == null) {
            repository.save(after);
            return;
        }

        before.resetToLowestPriceProduct(after);
        outboxService.complete(outboxEvent);
    }

    @Retryable(retryFor = OptimisticLockingFailureException.class, backoff = @Backoff(delay = 300), maxAttempts = 5)
    @Transactional(propagation = REQUIRES_NEW)
    @TransactionalEventListener
    public void productDeletedOutboxEventHandler(ProductDeletedOutboxEvent outboxEvent) {
        LowestPriceProductByCategory lowestPriceProductByCategory = repository.findByCategory(outboxEvent.getCategory().name());

        if (lowestPriceProductByCategory == null) {
            return;
        }

        if (!lowestPriceProductByCategory.productId().equals(outboxEvent.getAggregateRootId())) {
            return;
        }

        LowestPriceProductByCategory after = findNewLowestPriceProductByCategory(outboxEvent.getCategory());

        lowestPriceProductByCategory.resetToNewLowestPriceProduct(after);
        outboxService.complete(outboxEvent);
    }

    @Retryable(retryFor = OptimisticLockingFailureException.class, backoff = @Backoff(delay = 300), maxAttempts = 5)
    @Transactional(propagation = REQUIRES_NEW)
    @TransactionalEventListener
    public void productPriceChangedOutboxEventHandler(ProductPriceChangedOutboxEvent outboxEvent) {
        LowestPriceProductByCategory before = repository.findByCategory(outboxEvent.getCategory().name());

        if (before == null) {
            return;
        }

        // 가격이 변경된 상품이 카테고리별 최저가 상품이고 기존 가격보다 더 큰 가격으로 변경됐을 시, 카테고리의 최저가 상품을 다시 구해야한다.
        if (before.productId().equals(outboxEvent.getAggregateRootId()) && before.price() < outboxEvent.getAfterPrice()) {
            LowestPriceProductByCategory after = findNewLowestPriceProductByCategory(outboxEvent.getCategory());
            before.resetToNewLowestPriceProduct(after);
            return;
        }

        Brand brand = brandRepository.findById(outboxEvent.getBrandId())
                .orElseThrow(() -> new DomainException(BRAND_NOT_EXIST));

        LowestPriceProductByCategory after = new LowestPriceProductByCategory(
                outboxEvent.getAggregateRootId(),
                outboxEvent.getCategory().name(),
                brand.name(),
                outboxEvent.getAfterPrice()
        );

        before.resetToLowestPriceProduct(after);
        outboxService.complete(outboxEvent);
    }

    @Retryable(retryFor = OptimisticLockingFailureException.class, backoff = @Backoff(delay = 300), maxAttempts = 5)
    @Transactional(propagation = REQUIRES_NEW)
    @TransactionalEventListener
    public void brandDeletedOutboxEventHandler(BrandDeletedOutboxEvent outboxEvent) {
        List<LowestPriceProductByCategory> beforeList =
                repository.findAllByBrandName(outboxEvent.getBrandName());

        for (LowestPriceProductByCategory before : beforeList) {
            Product lowestPriceProduct =
                    productRepository.findTopByCategoryOrderByPriceAscIdDesc(Category.from(before.category()));

            Brand brand = brandRepository.findById(lowestPriceProduct.brandId())
                    .orElseThrow(() -> new DomainException(BRAND_NOT_EXIST));

            LowestPriceProductByCategory after = new LowestPriceProductByCategory(
                    lowestPriceProduct.id(),
                    lowestPriceProduct.category().name(),
                    brand.name(),
                    lowestPriceProduct.price().value()
            );
            before.resetToNewLowestPriceProduct(after);
        }
        outboxService.complete(outboxEvent);
    }

    private LowestPriceProductByCategory findNewLowestPriceProductByCategory(Category category) {
        Product lowestPriceProduct = productRepository.findTopByCategoryOrderByPriceAscIdDesc(category);
        Brand brand = brandRepository.findById(lowestPriceProduct.brandId())
                .orElseThrow(() -> new DomainException(BRAND_NOT_EXIST));

        LowestPriceProductByCategory after = new LowestPriceProductByCategory(
                lowestPriceProduct.id(),
                lowestPriceProduct.category().name(),
                brand.name(),
                lowestPriceProduct.price().value()
        );
        return after;
    }

    @Recover
    public void productRegisteredOutboxEventHandlerRecover(
            OptimisticLockingFailureException exception,
            ProductRegisteredOutboxEvent outboxEvent
    ) {
        log.error(RETRY_FAILED.message() + "\n" + outboxEvent);
    }

    @Recover
    public void productDeletedOutboxEventHandler(
            OptimisticLockingFailureException exception,
            ProductDeletedOutboxEvent outboxEvent
    ) {
        log.error(RETRY_FAILED.message() + "\n" + outboxEvent);
    }

    @Recover
    public void productPriceChangedOutboxEventHandler(
            OptimisticLockingFailureException exception,
            ProductPriceChangedOutboxEvent outboxEvent
    ) {
        log.error(RETRY_FAILED.message() + "\n" + outboxEvent);
    }

    @Recover
    public void brandDeletedOutboxEventHandler(
            OptimisticLockingFailureException exception,
            BrandDeletedOutboxEvent outboxEvent
    ) {
        log.error(RETRY_FAILED.message() + "\n" + outboxEvent);
    }
}
