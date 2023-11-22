package com.toy.coordination.query.application;

import com.toy.coordination.command.domain.LowestPriceProductByCategory;
import com.toy.coordination.command.domain.LowestPriceProductByCategoryRepository;
import com.toy.coordination.query.presentation.dto.LowestPriceProductByCategoryResponse;
import com.toy.coordination.query.presentation.dto.LowestPriceProductsByCategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LowestPriceProductByCategoryQueryService {
    private final LowestPriceProductByCategoryRepository repository;

    @Autowired
    public LowestPriceProductByCategoryQueryService(LowestPriceProductByCategoryRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public LowestPriceProductsByCategoryResponse query() {
        List<LowestPriceProductByCategory> lowestPriceProductByCategories = repository.findAll();

        List<LowestPriceProductByCategoryResponse> lowestPriceProductByCategoryResponses = lowestPriceProductByCategories.stream()
                .map(lowestPriceProductByCategory -> new LowestPriceProductByCategoryResponse(
                        lowestPriceProductByCategory.category(),
                        lowestPriceProductByCategory.brandName(),
                        lowestPriceProductByCategory.price()
                ))
                .toList();

        int totalPrice = lowestPriceProductByCategoryResponses.stream()
                .mapToInt(LowestPriceProductByCategoryResponse::getPrice)
                .sum();

        return new LowestPriceProductsByCategoryResponse(lowestPriceProductByCategoryResponses, totalPrice);
    }
}
