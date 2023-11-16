package com.toy.product.command.domain.service;

import com.toy.common.exception.DomainException;

public interface ProductRegisterValidator {
    void validateBrandExists(Long brandId) throws DomainException;
}
