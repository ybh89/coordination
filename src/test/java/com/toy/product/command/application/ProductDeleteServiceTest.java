package com.toy.product.command.application;

import com.toy.product.command.domain.Product;
import com.toy.product.command.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;

@DisplayName("ProductDeleteService 단위테스트")
class ProductDeleteServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductDeleteService productDeleteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void delete_메소드_실행시_상품이_존재하면_productRepository의_delete_한번_실행해야한다() {
        // given
        Long productId = 1L;
        Product product = mock(Product.class);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // when
        productDeleteService.delete(productId);

        // then
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void delete_메소드_실행시_상품이_존재하지않으면_productRepository의_delete_메소드는_실행되지않아야한다() {
        // given
        Long productId = 2L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // when
        productDeleteService.delete(productId);

        // then
        verify(productRepository, never()).delete(any(Product.class));
    }
}