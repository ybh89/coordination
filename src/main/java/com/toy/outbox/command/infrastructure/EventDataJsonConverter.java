package com.toy.outbox.command.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.toy.outbox.command.domain.EventType;
import com.toy.outbox.command.domain.event.OutboxEvent;
import com.toy.outbox.command.domain.event.ProductPriceChangedOutboxEvent;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class EventDataJsonConverter implements AttributeConverter<Object, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Object attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new RuntimeException("JSON writing error", e);
        }
    }

    @Override
    public Object convertToEntityAttribute(String dbData) {
        try {
            ObjectNode node = objectMapper.readValue(dbData, ObjectNode.class);
            EventType eventType = EventType.valueOf(node.get("eventType").asText());

            switch (eventType) {
                case PRODUCT_PRICE_CHANGED:
                    return objectMapper.treeToValue(node, ProductPriceChangedOutboxEvent.class);
                default:
                    return objectMapper.treeToValue(node, OutboxEvent.class);
            }
        } catch (Exception e) {
            throw new RuntimeException("JSON reading error", e);
        }
    }
}