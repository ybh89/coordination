package com.toy.brand.command.application;

import com.toy.brand.command.domain.Brand;
import com.toy.brand.command.domain.BrandRepository;
import com.toy.common.exception.DomainException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static com.toy.common.exception.ExceptionCode.BRAND_NOT_EXIST;
import static org.mockito.Mockito.*;

@DisplayName("BrandModifyService 단위테스")
class BrandModifyServiceTest {
    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private BrandModifyService brandModifyService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void modify_메소드_실행하면_brand의_modify_메소드가_실행해야한다() {
        // given
        Long brandId = 1L;
        String newName = "New Brand Name";
        Brand brand = mock(Brand.class);
        when(brandRepository.findById(brandId)).thenReturn(Optional.of(brand));

        // when
        brandModifyService.modify(brandId, newName);

        // then
        verify(brand, times(1)).modify(any(Brand.class));
    }

    @Test
    void testModifyNonExistingBrand() {
        // given
        Long brandId = 2L;
        when(brandRepository.findById(brandId)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> brandModifyService.modify(brandId, "New Brand Name"))
                .isInstanceOf(DomainException.class)
                .hasMessage(BRAND_NOT_EXIST.message());
    }
}