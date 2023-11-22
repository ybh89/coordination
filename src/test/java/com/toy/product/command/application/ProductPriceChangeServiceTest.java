package com.toy.product.command.application;

import com.toy.common.exception.DomainException;
import com.toy.outbox.command.application.OutboxService;
import com.toy.outbox.command.domain.event.ProductPriceChangedOutboxEvent;
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
    ProductRepository productRepository;

    @Mock
    OutboxService outboxService;

    @InjectMocks
    ProductPriceChangeService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void change_메소드_실행하면_ProductRepository의_findById_메소드가_한번_실행된다() {
        // given
        Long productId = 1L;
        int newPrice = 1000;
        Product product = Product.create(1L, "TOP", 10, id -> {
        });
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        doNothing().when(outboxService).register(any(ProductPriceChangedOutboxEvent.class));

        // when
        service.change(productId, newPrice);

        // then
        verify(productRepository, times(1)).findById(productId);
        verify(outboxService).register(any(ProductPriceChangedOutboxEvent.class));
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