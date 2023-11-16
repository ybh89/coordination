package com.toy.product.command.domain;

import com.toy.common.exception.DomainException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.toy.common.exception.ExceptionCode.PRODUCT_PRICE_IS_NEGATIVE;

@DisplayName("Price 단위테스트")
class PriceTest {
    @Test
    void 생성시_값이_음수이면_에러() {
        Assertions.assertThatThrownBy(() -> Price.from(-1))
                .isInstanceOf(DomainException.class)
                .hasMessage(PRODUCT_PRICE_IS_NEGATIVE.message());
    }

    @ValueSource(ints = {0, Integer.MAX_VALUE})
    @ParameterizedTest
    void 생성시_값이_0보다크면_정상실행(int value) {
        Assertions.assertThatCode(() -> Price.from(value))
                .doesNotThrowAnyException();
    }
}