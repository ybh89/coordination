package com.toy.brand.command.domain;

import com.toy.common.exception.DomainException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.toy.common.exception.ExceptionCode.BRAND_NAME_NOT_EXIST;

@DisplayName("Brand 단위테스트")
class BrandTest {
    @Test
    void 생성시_이름이_null이면_에러() {
        Assertions.assertThatThrownBy(() -> Brand.create(null))
                .isInstanceOf(DomainException.class)
                .hasMessage(BRAND_NAME_NOT_EXIST.message());
    }

    @ValueSource(strings = {"", " ", "  "})
    @ParameterizedTest
    void 생성시_이름이_비어있으면_에러(String name) {
        Assertions.assertThatThrownBy(() -> Brand.create(name))
                .isInstanceOf(DomainException.class)
                .hasMessage(BRAND_NAME_NOT_EXIST.message());
    }

    @Test
    void 정상생성() {
        Assertions.assertThatCode(() -> Brand.create("A"))
                .doesNotThrowAnyException();
    }
}