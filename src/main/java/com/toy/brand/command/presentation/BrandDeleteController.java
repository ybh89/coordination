package com.toy.brand.command.presentation;

import com.toy.brand.command.application.BrandDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/brands")
@RestController
public class BrandDeleteController {
    private final BrandDeleteService brandDeleteService;

    @Autowired
    public BrandDeleteController(BrandDeleteService brandDeleteService) {
        this.brandDeleteService = brandDeleteService;
    }

    @DeleteMapping("/{brandId}")
    public ResponseEntity<Void> delete(@PathVariable Long brandId) {
        brandDeleteService.delete(brandId);
        return ResponseEntity.noContent().build();
    }
}
