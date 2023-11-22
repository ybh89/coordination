package com.toy.coordination.query.presentation.dto;

import lombok.Getter;

@Getter
public class LowestPriceProductByCategoryResponse {
    private final String category;
    private final String brandName;
    private final int price;

    public LowestPriceProductByCategoryResponse(String category, String brandName, int price) {
        this.category = category;
        this.brandName = brandName;
        this.price = price;
    }
}
