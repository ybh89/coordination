package com.toy.product.command.presentation.dto;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;

@Getter
public class ProductPriceChangeRequest {
    @PositiveOrZero
    private final int price;

    private ProductPriceChangeRequest() {
        this.price = -1;
    }

    public ProductPriceChangeRequest(int price) {
        this.price = price;
    }
}
