package com.toy.brand.command.application;

import com.toy.brand.command.domain.Brand;
import com.toy.brand.command.domain.BrandRepository;
import com.toy.common.exception.DomainException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.toy.common.exception.ExceptionCode.BRAND_NOT_EXIST;

@Service
public class BrandModifyService {
    private final BrandRepository brandRepository;

    @Autowired
    public BrandModifyService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Transactional
    public void modify(Long brandId, String name) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new DomainException(BRAND_NOT_EXIST));

        brand.modify(Brand.create(name));
    }
}
