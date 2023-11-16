package com.toy.product.command.domain;

import com.toy.common.exception.DomainException;
import com.toy.product.command.domain.service.ProductRegisterValidator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.toy.common.exception.ExceptionCode.BRAND_ID_IS_NULL;

@DisplayName("Product 단위테스트")
class ProductTest {
    ProductRegisterValidator productRegisterValidator = brandId -> {};

    @Test
    void 생성시_브랜드아이디_null이면_에러() {
        Assertions.assertThatThrownBy(() -> Product.create(null, "TOP", 1000, productRegisterValidator))
                .isInstanceOf(DomainException.class)
                .hasMessage(BRAND_ID_IS_NULL.message());
    }
}