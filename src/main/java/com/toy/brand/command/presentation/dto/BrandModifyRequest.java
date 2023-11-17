package com.toy.brand.command.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class BrandModifyRequest {
    @NotBlank
    private final String name;

    private BrandModifyRequest() {
        this.name = null;
    }

    public BrandModifyRequest(String name) {
        this.name = name;
    }
}
