package com.toy.brand.command.presentation;

import com.toy.brand.command.application.BrandRegisterService;
import com.toy.brand.command.presentation.dto.BrandRegisterRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequestMapping("/brands")
@RestController
public class BrandRegisterController {
    private final BrandRegisterService brandRegisterService;

    @Autowired
    public BrandRegisterController(BrandRegisterService brandRegisterService) {
        this.brandRegisterService = brandRegisterService;
    }

    @PostMapping
    public ResponseEntity<Void> register(@RequestBody @Valid BrandRegisterRequest brandRegisterRequest) {
        Long brandId = brandRegisterService.register(brandRegisterRequest.getName());
        return ResponseEntity.created(URI.create("/brands/" + brandId)).build();
    }
}
