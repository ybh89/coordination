package com.toy.brand.command.application;

import com.toy.brand.command.domain.Brand;
import com.toy.brand.command.domain.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BrandDeleteService {
    private final BrandRepository brandRepository;

    @Autowired
    public BrandDeleteService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Transactional
    public void delete(Long brandId) {
        Brand brand = brandRepository.findById(brandId).orElse(null);

        if (brand == null) {
            return;
        }
        brandRepository.delete(brand);
    }
}
