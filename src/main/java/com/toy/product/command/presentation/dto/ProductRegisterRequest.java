package com.toy.product.command.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;

@Getter
public class ProductRegisterRequest {
    @PositiveOrZero
    @NotNull
    private final Long brandId;

    @NotBlank
    private final String category;

    @PositiveOrZero
    private final int price;

    private ProductRegisterRequest() {
        this.brandId = null;
        this.category = null;
        this.price = -1;
    }

    public ProductRegisterRequest(Long brandId, String category, int price) {
        this.brandId = brandId;
        this.category = category;
        this.price = price;
    }
}
