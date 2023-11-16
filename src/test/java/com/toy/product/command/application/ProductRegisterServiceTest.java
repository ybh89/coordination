package com.toy.product.command.application;

import com.toy.product.command.domain.Product;
import com.toy.product.command.domain.ProductRepository;
import com.toy.product.command.domain.service.ProductRegisterValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("ProductRegisterService 단위테스트")
class ProductRegisterServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductRegisterValidator productRegisterValidator;

    @InjectMocks
    private ProductRegisterService productRegisterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_메소드_실행하면_productRepository의_save메소드_한번_실행해야한다() {
        // given
        Long brandId = 1L;
        String category = "TOP";
        int price = 1000;
        Product mockProduct = Product.create(brandId, category, price, productRegisterValidator);
        when(productRepository.save(any(Product.class))).thenReturn(mockProduct);

        // when
        productRegisterService.register(brandId, category, price);

        // then
        verify(productRepository, times(1)).save(any(Product.class));
    }
}