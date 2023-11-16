package com.toy.product.command.presentation;

import com.toy.product.command.application.ProductRegisterService;
import com.toy.product.command.presentation.dto.ProductRegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@DisplayName("ProductRegisterController 단위테스트")
class ProductRegisterControllerTest {
    @Mock
    private ProductRegisterService productRegisterService;

    @InjectMocks
    private ProductRegisterController productRegisterController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_메소드_실행시_productRegisterService의_register_메소드가_한번_실행해야한다() {
        // given
        ProductRegisterRequest request = new ProductRegisterRequest(1L, "TOP", 1000);
        Long expectedProductId = 1L;
        when(productRegisterService.register(anyLong(), anyString(), anyInt())).thenReturn(expectedProductId);

        // when
        productRegisterController.register(request);

        // then
        verify(productRegisterService, times(1)).register(1L, "TOP", 1000);
    }
}