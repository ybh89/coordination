package com.toy.outbox.command.domain.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.toy.outbox.command.domain.EventType;
import lombok.Getter;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = BrandDeletedOutboxEvent.class, name = "brandDeleted"),
        @JsonSubTypes.Type(value = ProductRegisteredOutboxEvent.class, name = "productRegistered"),
        @JsonSubTypes.Type(value = ProductDeletedOutboxEvent.class, name = "productDeleted"),
        @JsonSubTypes.Type(value = ProductPriceChangedOutboxEvent.class, name = "productPriceChanged")
})
@Getter
public class OutboxEvent {
    private Long id;
    protected final Long aggregateRootId;
    protected final EventType eventType;

    private OutboxEvent() {
        this.aggregateRootId = null;
        this.eventType = null;
    }

    public OutboxEvent(Long aggregateRootId, EventType eventType) {
        this.aggregateRootId = aggregateRootId;
        this.eventType = eventType;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
