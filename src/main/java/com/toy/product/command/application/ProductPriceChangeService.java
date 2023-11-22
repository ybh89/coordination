package com.toy.product.command.application;

import com.toy.common.exception.DomainException;
import com.toy.outbox.command.application.OutboxService;
import com.toy.outbox.command.domain.event.ProductPriceChangedOutboxEvent;
import com.toy.product.command.domain.Product;
import com.toy.product.command.domain.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.toy.common.exception.ExceptionCode.PRODUCT_NOT_EXIST;
import static com.toy.outbox.command.domain.EventType.PRODUCT_PRICE_CHANGED;

@Service
public class ProductPriceChangeService {
    private final ProductRepository productRepository;
    private final OutboxService outboxService;

    @Autowired
    public ProductPriceChangeService(ProductRepository productRepository, OutboxService outboxService) {
        this.productRepository = productRepository;
        this.outboxService = outboxService;
    }

    @Transactional
    public void change(Long productId, int price) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new DomainException(PRODUCT_NOT_EXIST, "product id: " + productId));

        outboxService.register(
                ProductPriceChangedOutboxEvent.of(
                        product,
                        PRODUCT_PRICE_CHANGED,
                        price
                )
        );
        product.changePrice(price);
    }
}
