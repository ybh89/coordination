package com.toy.brand.command.application;

import com.toy.brand.command.domain.Brand;
import com.toy.brand.command.domain.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BrandRegisterService {
    private final BrandRepository brandRepository;

    @Autowired
    public BrandRegisterService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Transactional
    public Long register(String name) {
        Brand brand = Brand.create(name);
        brandRepository.save(brand);
        return brand.id();
    }
}
