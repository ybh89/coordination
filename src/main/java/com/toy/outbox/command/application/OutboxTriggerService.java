package com.toy.outbox.command.application;

import com.toy.coordination.command.application.LowestPriceProductByCategoryUpdateService;
import com.toy.outbox.command.domain.EventStatus;
import com.toy.outbox.command.domain.Outbox;
import com.toy.outbox.command.domain.OutboxRepository;
import com.toy.outbox.command.domain.event.BrandDeletedOutboxEvent;
import com.toy.outbox.command.domain.event.ProductDeletedOutboxEvent;
import com.toy.outbox.command.domain.event.ProductPriceChangedOutboxEvent;
import com.toy.outbox.command.domain.event.ProductRegisteredOutboxEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OutboxTriggerService {
    private final OutboxRepository outboxRepository;
    private final LowestPriceProductByCategoryUpdateService lowestPriceProductByCategoryUpdateService;

    @Autowired
    public OutboxTriggerService(
            OutboxRepository outboxRepository,
            LowestPriceProductByCategoryUpdateService lowestPriceProductByCategoryUpdateService
    ) {
        this.outboxRepository = outboxRepository;
        this.lowestPriceProductByCategoryUpdateService = lowestPriceProductByCategoryUpdateService;
    }

    @Scheduled(fixedDelay = 1000 * 60 * 60)
    public void process() {
        List<Outbox> outboxList = outboxRepository.findAllByEventStatus(EventStatus.WAITING);
        for (Outbox outbox : outboxList) {
            switch (outbox.getEventType()) {
                case BRAND_DELETED -> {
                    BrandDeletedOutboxEvent eventData = (BrandDeletedOutboxEvent) outbox.getEventData();
                    lowestPriceProductByCategoryUpdateService.brandDeletedOutboxEventHandler(eventData);
                }
                case PRODUCT_REGISTERED -> {
                    ProductRegisteredOutboxEvent eventData = (ProductRegisteredOutboxEvent) outbox.getEventData();
                    lowestPriceProductByCategoryUpdateService.productRegisteredOutboxEventHandler(eventData);
                }
                case PRODUCT_PRICE_CHANGED -> {
                    ProductPriceChangedOutboxEvent eventData = (ProductPriceChangedOutboxEvent) outbox.getEventData();
                    lowestPriceProductByCategoryUpdateService.productPriceChangedOutboxEventHandler(eventData);
                }
                case PRODUCT_DELETED -> {
                    ProductDeletedOutboxEvent eventData = (ProductDeletedOutboxEvent) outbox.getEventData();
                    lowestPriceProductByCategoryUpdateService.productDeletedOutboxEventHandler(eventData);
                }
            }
        }
    }
}
