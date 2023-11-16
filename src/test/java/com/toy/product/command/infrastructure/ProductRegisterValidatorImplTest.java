package com.toy.product.command.infrastructure;

import com.toy.brand.command.domain.BrandRepository;
import com.toy.common.exception.DomainException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.toy.common.exception.ExceptionCode.BRAND_NOT_EXIST;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@DisplayName("ProductRegisterValidatorImpl 단위테스트")
class ProductRegisterValidatorImplTest {
    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private ProductRegisterValidatorImpl productRegisterValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validateBrandExists_메서드_실행하면_brandRepository의_existsById_메소드가_한번_실행된다() {
        // given
        Long brandId = 1L;
        when(brandRepository.existsById(brandId)).thenReturn(true);

        // when
        productRegisterValidator.validateBrandExists(brandId);

        // then
        verify(brandRepository, times(1)).existsById(brandId);
    }

    @Test
    void validateBrandExists_브랜드가_존재하지않으면_예외() {
        // given
        Long brandId = 2L;
        when(brandRepository.existsById(brandId)).thenReturn(false);

        // when
        // then
        assertThatThrownBy(() -> productRegisterValidator.validateBrandExists(brandId))
                .isInstanceOf(DomainException.class)
                .hasMessageContaining(BRAND_NOT_EXIST.message());
    }
}