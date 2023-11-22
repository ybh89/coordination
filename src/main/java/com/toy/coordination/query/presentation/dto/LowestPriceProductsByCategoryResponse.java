package com.toy.coordination.query.presentation.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class LowestPriceProductsByCategoryResponse {
    private final List<LowestPriceProductByCategoryResponse> lowestPriceProductByCategoryResponses;
    private final int totalPrice;

    public LowestPriceProductsByCategoryResponse(List<LowestPriceProductByCategoryResponse> lowestPriceProductByCategoryResponses, int totalPrice) {
        this.lowestPriceProductByCategoryResponses = lowestPriceProductByCategoryResponses;
        this.totalPrice = totalPrice;
    }
}
