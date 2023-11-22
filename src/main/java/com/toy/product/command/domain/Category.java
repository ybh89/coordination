package com.toy.product.command.domain;

import com.toy.common.exception.DomainException;

import static com.toy.common.exception.ExceptionCode.CATEGORY_VALUE_IS_INVALID;

public enum Category {
    TOP,
    OUTWEAR,
    PANTS,
    SNEAKERS,
    BAG,
    HAT,
    SOCKS,
    ACCESSORIES
    ;

    public static Category from(String value) {
        try {
            return Category.valueOf(value);
        } catch (IllegalArgumentException exception) {
            throw new DomainException(CATEGORY_VALUE_IS_INVALID);
        }
    }
}
