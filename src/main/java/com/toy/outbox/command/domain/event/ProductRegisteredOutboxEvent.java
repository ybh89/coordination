package com.toy.outbox.command.domain.event;

import com.toy.outbox.command.domain.EventType;
import com.toy.product.command.domain.Product;
import lombok.Getter;

@Getter
public class ProductRegisteredOutboxEvent extends OutboxEvent {
    private final int price;
    private final String category;
    private final Long brandId;

    private ProductRegisteredOutboxEvent() {
        super(null, null);
        this.price = -1;
        this.category = null;
        this.brandId = null;
    }

    private ProductRegisteredOutboxEvent(
            Long aggregateRootId,
            EventType eventType,
            int price,
            String category,
            Long brandId
    ) {
        super(aggregateRootId, eventType);
        this.price = price;
        this.category = category;
        this.brandId = brandId;
    }

    public static ProductRegisteredOutboxEvent of(Product product, EventType eventType) {
        return new ProductRegisteredOutboxEvent(
                product.id(),
                eventType,
                product.price().value(),
                product.category().name(),
                product.brandId()
        );
    }

    @Override
    public String toString() {
        return "ProductRegisteredOutboxEvent{" +
                "aggregateRootId=" + aggregateRootId +
                ", eventType=" + eventType +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", brandId=" + brandId +
                '}';
    }
}
