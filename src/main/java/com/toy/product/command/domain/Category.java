package com.toy.product.command.domain;

import com.toy.common.exception.DomainException;

import static com.toy.common.exception.ExceptionCode.CATEGORY_VALUE_IS_INVALID;

public enum Category {
    TOP, // 상의
    OUTWEAR, // 아우터
    PANTS, // 바지
    SNEAKERS, // 스니커즈
    BAG, // 가방
    HAT, // 모자
    SOCKS, // 양말
    ACCESSORIES // 악세사리
    ;

    public static Category from(String value) {
        try {
            return Category.valueOf(value);
        } catch (IllegalArgumentException exception) {
            throw new DomainException(CATEGORY_VALUE_IS_INVALID);
        }
    }
}
