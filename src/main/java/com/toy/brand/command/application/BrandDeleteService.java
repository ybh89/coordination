package com.toy.brand.command.application;

import com.toy.brand.command.domain.Brand;
import com.toy.brand.command.domain.BrandRepository;
import com.toy.outbox.command.application.OutboxService;
import com.toy.outbox.command.domain.event.BrandDeletedOutboxEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.toy.outbox.command.domain.EventType.BRAND_DELETED;

@Service
public class BrandDeleteService {
    private final BrandRepository brandRepository;
    private final OutboxService outboxService;

    @Autowired
    public BrandDeleteService(BrandRepository brandRepository, OutboxService outboxService) {
        this.brandRepository = brandRepository;
        this.outboxService = outboxService;
    }

    @Transactional
    public void delete(Long brandId) {
        Brand brand = brandRepository.findById(brandId).orElse(null);

        if (brand == null) {
            return;
        }
        outboxService.register(new BrandDeletedOutboxEvent(brandId, BRAND_DELETED, brand.name()));
        brandRepository.delete(brand);
    }
}
