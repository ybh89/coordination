package com.toy.brand.command.presentation;

import com.toy.brand.command.application.BrandRegisterService;
import com.toy.brand.command.presentation.dto.BrandRegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

@DisplayName("BrandRegisterController 단위테스트")
class BrandRegisterControllerTest {
    @Mock
    private BrandRegisterService brandRegisterService;

    @InjectMocks
    private BrandRegisterController brandRegisterController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_메소드_실행시_brandRegisterService의_register_메소드_한번_실행해야한다() {
        // given
        BrandRegisterRequest request = new BrandRegisterRequest("Test Brand");
        Long expectedBrandId = 1L;
        when(brandRegisterService.register("Test Brand")).thenReturn(expectedBrandId);

        // when
        brandRegisterController.register(request);

        // then
        verify(brandRegisterService, times(1)).register("Test Brand");
    }
}