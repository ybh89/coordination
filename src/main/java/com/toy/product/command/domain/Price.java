package com.toy.product.command.domain;

import com.toy.common.exception.DomainException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

import static com.toy.common.exception.ExceptionCode.PRODUCT_PRICE_IS_NEGATIVE;

@EqualsAndHashCode(of = "value")
@Embeddable
public class Price {
    public static final int MINIMUM_VALUE = 0;
    @Column(name = "price", nullable = false)
    private final int value;

    @Deprecated
    protected Price() {
        this.value = -1;
    }

    private Price(int value) {
        this.value = value;
    }

    public static Price from(int value) {
        validate(value);
        return new Price(value);
    }

    private static void validate(int value) {
        if (value < MINIMUM_VALUE) {
            throw new DomainException(PRODUCT_PRICE_IS_NEGATIVE);
        }
    }

    public int value() {
        return value;
    }
}
