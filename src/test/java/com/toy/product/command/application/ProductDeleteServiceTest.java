package com.toy.product.command.application;

import com.toy.common.exception.DomainException;
import com.toy.outbox.command.application.OutboxService;
import com.toy.outbox.command.domain.EventType;
import com.toy.outbox.command.domain.event.BrandDeletedOutboxEvent;
import com.toy.outbox.command.domain.event.ProductDeletedOutboxEvent;
import com.toy.product.command.domain.Product;
import com.toy.product.command.domain.ProductRepository;
import com.toy.product.command.domain.service.ProductRegisterValidator;
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
    ProductRepository productRepository;

    @Mock
    OutboxService outboxService;

    @InjectMocks
    ProductDeleteService productDeleteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void delete_메소드_실행시_상품이_존재하면_productRepository의_delete_한번_실행해야한다() {
        // given
        Long productId = 1L;
        Product mockProduct = Product.create(1L, "TOP", 1000, brandId -> {});
        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));
        doNothing().when(outboxService).register(any(ProductDeletedOutboxEvent.class));

        // when
        productDeleteService.delete(productId);

        // then
        verify(productRepository).delete(mockProduct);
        verify(outboxService).register(any(ProductDeletedOutboxEvent.class));
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
        verify(outboxService, never()).register(any());
    }

    @Test
    void deleteByBrand_메소드_실행시_deleteAllByBrandId_메소드_실행해야한다() {
        // given
        Long brandId = 1L;
        BrandDeletedOutboxEvent event = new BrandDeletedOutboxEvent(brandId, EventType.BRAND_DELETED, "A");

        // then
        productDeleteService.deleteByBrand(event);

        // when
        verify(productRepository).deleteAllByBrandId(brandId);
    }
}