package com.toy.product.command.presentation;

import com.toy.product.command.application.ProductRegisterService;
import com.toy.product.command.presentation.dto.ProductRegisterRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequestMapping("/products")
@RestController
public class ProductRegisterController {
    private final ProductRegisterService productRegisterService;

    @Autowired
    public ProductRegisterController(ProductRegisterService productRegisterService) {
        this.productRegisterService = productRegisterService;
    }

    @PostMapping
    public ResponseEntity<Void> register(@RequestBody @Valid ProductRegisterRequest productRegisterRequest) {
        Long productId = productRegisterService.register(
                productRegisterRequest.getBrandId(),
                productRegisterRequest.getCategory(),
                productRegisterRequest.getPrice()
        );
        return ResponseEntity.created(URI.create("/products/" + productId)).build();
    }
}
