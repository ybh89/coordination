package com.toy.brand.command.application;

import com.toy.brand.command.domain.Brand;
import com.toy.brand.command.domain.BrandRepository;
import com.toy.outbox.command.application.OutboxService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;

@DisplayName("BrandDeleteService 단위테스트")
class BrandDeleteServiceTest {
    @Mock
    private BrandRepository brandRepository;

    @Mock
    private OutboxService outboxService;

    @InjectMocks
    private BrandDeleteService brandDeleteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void delete_메소드_실행시_브랜드가_존재하면_brandRepository의_delete_메소드_실행해야한다() {
        // given
        Long brandId = 1L;
        Brand brand = mock(Brand.class);
        when(brandRepository.findById(brandId)).thenReturn(Optional.of(brand));

        // when
        brandDeleteService.delete(brandId);

        // then
        verify(brandRepository, times(1)).delete(brand);
    }

    @Test
    void delete_메소드_실행시_브랜드가_존재하지않으면_brandRepository의_delete_메소드_실행되지않아야한다() {
        // given
        Long brandId = 2L;
        when(brandRepository.findById(brandId)).thenReturn(Optional.empty());

        // when
        brandDeleteService.delete(brandId);

        // then
        verify(brandRepository, never()).delete(any(Brand.class));
    }
}