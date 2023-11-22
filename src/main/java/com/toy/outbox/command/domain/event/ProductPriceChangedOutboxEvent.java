package com.toy.outbox.command.domain.event;

import com.toy.outbox.command.domain.EventType;
import com.toy.product.command.domain.Category;
import com.toy.product.command.domain.Product;
import lombok.Getter;

@Getter
public class ProductPriceChangedOutboxEvent extends OutboxEvent {
    private final int beforePrice;
    private final int afterPrice;
    private final Category category;
    private final Long brandId;

    private ProductPriceChangedOutboxEvent() {
        super(null, null);
        this.beforePrice = -1;
        this.afterPrice = -1;
        this.category = null;
        this.brandId = null;
    }

    private ProductPriceChangedOutboxEvent(Long aggregateRootId, EventType eventType, int beforePrice, int afterPrice, Category category, Long brandId) {
        super(aggregateRootId, eventType);
        this.beforePrice = beforePrice;
        this.afterPrice = afterPrice;
        this.category = category;
        this.brandId = brandId;
    }

    public static ProductPriceChangedOutboxEvent of(Product product, EventType eventType, int afterPrice) {
        return new ProductPriceChangedOutboxEvent(
                product.id(),
                eventType,
                product.price().value(),
                afterPrice,
                product.category(),
                product.brandId()
        );
    }
}
