package com.toy.product.command.application;

import com.toy.product.command.domain.Product;
import com.toy.product.command.domain.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductDeleteService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductDeleteService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public void delete(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);

        if (product == null) {
            return;
        }
        productRepository.delete(product);
    }
}
