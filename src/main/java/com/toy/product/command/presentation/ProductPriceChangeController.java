package com.toy.product.command.presentation;

import com.toy.product.command.application.ProductPriceChangeService;
import com.toy.product.command.presentation.dto.ProductPriceChangeRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/products")
@RestController
public class ProductPriceChangeController {
    private final ProductPriceChangeService productPriceChangeService;

    @Autowired
    public ProductPriceChangeController(ProductPriceChangeService productPriceChangeService) {
        this.productPriceChangeService = productPriceChangeService;
    }

    @PatchMapping("/{productId}/price")
    public ResponseEntity<Void> change(
            @PathVariable Long productId,
            @RequestBody @Valid ProductPriceChangeRequest productPriceChangeRequest
    ) {
        productPriceChangeService.change(productId, productPriceChangeRequest.getPrice());
        return ResponseEntity.ok().build();
    }
}
