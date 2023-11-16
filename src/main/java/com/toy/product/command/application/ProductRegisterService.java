package com.toy.product.command.application;

import com.toy.product.command.domain.Product;
import com.toy.product.command.domain.ProductRepository;
import com.toy.product.command.domain.service.ProductRegisterValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductRegisterService {
    private final ProductRepository productRepository;
    private final ProductRegisterValidator productRegisterValidator;

    @Autowired
    public ProductRegisterService(ProductRepository productRepository, ProductRegisterValidator productRegisterValidator) {
        this.productRepository = productRepository;
        this.productRegisterValidator = productRegisterValidator;
    }

    @Transactional
    public Long register(Long brandId, String category, int price) {
        Product product = Product.create(brandId, category, price, productRegisterValidator);
        productRepository.save(product);
        return product.id();
    }
}
