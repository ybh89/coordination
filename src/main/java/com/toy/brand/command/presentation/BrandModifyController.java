package com.toy.brand.command.presentation;

import com.toy.brand.command.application.BrandModifyService;
import com.toy.brand.command.presentation.dto.BrandModifyRequest;
import com.toy.brand.command.presentation.dto.BrandRegisterRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/brands")
@RestController
public class BrandModifyController {
    private final BrandModifyService brandModifyService;

    @Autowired
    public BrandModifyController(BrandModifyService brandModifyService) {
        this.brandModifyService = brandModifyService;
    }

    @PutMapping("/{brandId}")
    public ResponseEntity<Void> register(
            @PathVariable Long brandId,
            @RequestBody @Valid BrandModifyRequest brandModifyRequest
    ) {
        brandModifyService.modify(brandId, brandModifyRequest.getName());
        return ResponseEntity.ok().build();
    }
}
