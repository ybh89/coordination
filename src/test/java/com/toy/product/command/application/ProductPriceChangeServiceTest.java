package com.toy.product.command.application;

import com.toy.common.exception.DomainException;
import com.toy.product.command.domain.Product;
import com.toy.product.command.domain.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static com.toy.common.exception.ExceptionCode.PRODUCT_NOT_EXIST;
import static org.mockito.Mockito.*;

@DisplayName("ProductPriceChangeService 단위테스트")
class ProductPriceChangeServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductPriceChangeService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void change_메소드_실행하면_Product의_changePrice메소드와_ProductRepository의_findById_메소드가_한번_실행된다() {
        // given
        Long productId = 1L;
        int newPrice = 1000;
        Product product = mock(Product.class);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // when
        service.change(productId, newPrice);

        // then
        verify(product, times(1)).changePrice(newPrice);
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void change_메소드_실행시_상품이_존재하지_않으면_에러() {
        // given
        Long productId = 2L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // when
        // then
        Assertions.assertThatThrownBy(() -> service.change(productId, 1000))
                .isInstanceOf(DomainException.class)
                .hasMessageContaining(PRODUCT_NOT_EXIST.message());
    }
}