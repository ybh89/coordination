package com.toy.product.command.application;

import com.toy.outbox.command.application.OutboxService;
import com.toy.outbox.command.domain.event.ProductRegisteredOutboxEvent;
import com.toy.product.command.domain.Product;
import com.toy.product.command.domain.ProductRepository;
import com.toy.product.command.domain.service.ProductRegisterValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.toy.outbox.command.domain.EventType.PRODUCT_REGISTERED;

@Service
public class ProductRegisterService {
    private final ProductRepository productRepository;
    private final ProductRegisterValidator productRegisterValidator;
    private final OutboxService outboxService;

    @Autowired
    public ProductRegisterService(
            ProductRepository productRepository,
            ProductRegisterValidator productRegisterValidator,
            OutboxService outboxService
    ) {
        this.productRepository = productRepository;
        this.productRegisterValidator = productRegisterValidator;
        this.outboxService = outboxService;
    }

    @Transactional
    public Long register(Long brandId, String category, int price) {
        Product product = Product.create(brandId, category, price, productRegisterValidator);
        productRepository.save(product);
        outboxService.register(ProductRegisteredOutboxEvent.of(product, PRODUCT_REGISTERED));
        return product.id();
    }
}
