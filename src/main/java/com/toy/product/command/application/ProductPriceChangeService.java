package com.toy.product.command.application;

import com.toy.common.exception.DomainException;
import com.toy.product.command.domain.Product;
import com.toy.product.command.domain.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.toy.common.exception.ExceptionCode.PRODUCT_NOT_EXIST;

@Service
public class ProductPriceChangeService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductPriceChangeService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public void change(Long productId, int price) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new DomainException(PRODUCT_NOT_EXIST, "product id: " + productId));

        product.changePrice(price);
    }
}
