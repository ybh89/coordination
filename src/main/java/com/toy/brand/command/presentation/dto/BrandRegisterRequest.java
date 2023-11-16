package com.toy.brand.command.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class BrandRegisterRequest {
    @NotBlank
    private final String name;

    private BrandRegisterRequest() {
        this.name = null;
    }

    public BrandRegisterRequest(String name) {
        this.name = name;
    }
}
