package com.toy.product.command.domain;

import com.toy.common.exception.DomainException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.toy.common.exception.ExceptionCode.CATEGORY_VALUE_IS_INVALID;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Category 단위테스트")
class CategoryTest {

    @ValueSource(strings = {"TOP", "OUTWEAR", "PANTS", "SNEAKERS", "BAG", "HAT", "SOCKS", "ACCESSORIES"})
    @ParameterizedTest
    void from_메소드_실행시_정상값이면_정상실행(String value) {
        Assertions.assertThatCode(() -> Category.from(value))
                .doesNotThrowAnyException();
    }

    @ValueSource(strings = {"", " ", "A", "!"})
    @ParameterizedTest
    void from_메소드_실행시_정상값이아니면_에러(String value) {
        Assertions.assertThatCode(() -> Category.from(value))
                .isInstanceOf(DomainException.class)
                .hasMessage(CATEGORY_VALUE_IS_INVALID.message());
    }
}