package com.toy.outbox.command.domain.event;

import com.toy.outbox.command.domain.EventType;
import com.toy.product.command.domain.Category;
import com.toy.product.command.domain.Product;
import lombok.Getter;

@Getter
public class ProductDeletedOutboxEvent extends OutboxEvent {
    private final int price;
    private final Category category;

    private ProductDeletedOutboxEvent() {
        super(null, null);
        this.price = -1;
        this.category = null;
    }

    private ProductDeletedOutboxEvent(Long aggregateRootId, EventType eventType, int price, Category category) {
        super(aggregateRootId, eventType);
        this.price = price;
        this.category = category;
    }

    public static ProductDeletedOutboxEvent of(Product product, EventType eventType) {
        return new ProductDeletedOutboxEvent(
                product.id(),
                eventType,
                product.price().value(),
                product.category()
        );
    }
}
