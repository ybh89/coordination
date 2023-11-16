package com.toy.brand.command.application;

import com.toy.brand.command.domain.Brand;
import com.toy.brand.command.domain.BrandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("BrandRegisterService 단위테스트")
class BrandRegisterServiceTest {
    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private BrandRegisterService brandRegisterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_메소드_실행시_brandRepository의_save_메소드가_한번_실행해야한다() {
        // 준비
        String brandName = "Test Brand";
        Brand mockBrand = Brand.create(brandName);
        when(brandRepository.save(any(Brand.class))).thenReturn(mockBrand);

        // 실행
        brandRegisterService.register(brandName);

        // 검증
        verify(brandRepository, times(1)).save(any(Brand.class));
    }
}