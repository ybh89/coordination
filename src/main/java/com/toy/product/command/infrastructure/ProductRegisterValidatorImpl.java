package com.toy.product.command.infrastructure;

import com.toy.brand.command.domain.BrandRepository;
import com.toy.common.exception.DomainException;
import com.toy.product.command.domain.service.ProductRegisterValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.toy.common.exception.ExceptionCode.BRAND_NOT_EXIST;

@Service
public class ProductRegisterValidatorImpl implements ProductRegisterValidator {
    private final BrandRepository brandRepository;

    @Autowired
    public ProductRegisterValidatorImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }


    @Override
    public void validateBrandExists(Long brandId) throws DomainException {
        if (!brandRepository.existsById(brandId)) {
            throw new DomainException(BRAND_NOT_EXIST, "브랜드 id: " + brandId);
        }
    }
}
