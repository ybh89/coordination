package com.toy.product.command.presentation;

import com.toy.product.command.application.ProductDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/products")
@RestController
public class ProductDeleteController {
    private final ProductDeleteService productDeleteService;

    @Autowired
    public ProductDeleteController(ProductDeleteService productDeleteService) {
        this.productDeleteService = productDeleteService;
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> delete(@PathVariable Long productId) {
        productDeleteService.delete(productId);
        return ResponseEntity.noContent().build();
    }
}
