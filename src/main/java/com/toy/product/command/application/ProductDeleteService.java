package com.toy.product.command.application;

import com.toy.outbox.command.application.OutboxService;
import com.toy.outbox.command.domain.event.BrandDeletedOutboxEvent;
import com.toy.outbox.command.domain.event.ProductDeletedOutboxEvent;
import com.toy.product.command.domain.Product;
import com.toy.product.command.domain.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.toy.outbox.command.domain.EventType.PRODUCT_DELETED;

@Service
public class ProductDeleteService {
    private final ProductRepository productRepository;
    private final OutboxService outboxService;

    @Autowired
    public ProductDeleteService(ProductRepository productRepository, OutboxService outboxService) {
        this.productRepository = productRepository;
        this.outboxService = outboxService;
    }

    @Transactional
    public void delete(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);

        if (product == null) {
            return;
        }
        outboxService.register(ProductDeletedOutboxEvent.of(product, PRODUCT_DELETED));
        productRepository.delete(product);
    }

    @EventListener
    @Transactional
    public void deleteByBrand(BrandDeletedOutboxEvent brandDeletedOutboxEvent) {
        productRepository.deleteAllByBrandId(brandDeletedOutboxEvent.getAggregateRootId());
    }
}
