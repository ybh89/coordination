package com.toy.outbox.command.domain.event;

import com.toy.outbox.command.domain.EventType;
import lombok.Getter;

@Getter
public class BrandDeletedOutboxEvent extends OutboxEvent {
    private final String brandName;

    private BrandDeletedOutboxEvent() {
        super(null, null);
        this.brandName = null;
    }

    public BrandDeletedOutboxEvent(Long aggregateRootId, EventType eventType, String brandName) {
        super(aggregateRootId, eventType);
        this.brandName = brandName;
    }
}
