package com.toy.coordination.query.presentation;

import com.toy.coordination.query.application.LowestPriceProductByCategoryQueryService;
import com.toy.coordination.query.presentation.dto.LowestPriceProductsByCategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/lowest-price-product-by-category")
@RestController
public class LowestPriceProductByCategoryController {
    private final LowestPriceProductByCategoryQueryService lowestPriceProductByCategoryQueryService;

    @Autowired
    public LowestPriceProductByCategoryController(LowestPriceProductByCategoryQueryService lowestPriceProductByCategoryQueryService) {
        this.lowestPriceProductByCategoryQueryService = lowestPriceProductByCategoryQueryService;
    }

    @GetMapping
    public ResponseEntity<LowestPriceProductsByCategoryResponse> query() {
        LowestPriceProductsByCategoryResponse response = lowestPriceProductByCategoryQueryService.query();
        return ResponseEntity.ok(response);
    }
}
