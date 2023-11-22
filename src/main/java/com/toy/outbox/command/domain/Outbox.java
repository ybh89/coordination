package com.toy.outbox.command.domain;

import com.toy.outbox.command.domain.event.BrandDeletedOutboxEvent;
import com.toy.outbox.command.domain.event.OutboxEvent;
import com.toy.outbox.command.infrastructure.EventDataJsonConverter;
import jakarta.persistence.*;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Outbox {
    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    @Column(name = "aggregate_root_id", nullable = false)
    private Long aggregateRootId;

    @Enumerated(STRING)
    @Column(name = "event", nullable = false)
    private EventType eventType;

    @Convert(converter = EventDataJsonConverter.class)
    @Column(name = "event_data", nullable = false)
    private Object eventData;

    @Enumerated(STRING)
    @Column(name = "event_status", nullable = false)
    private EventStatus eventStatus;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Deprecated
    protected Outbox() {
    }

    private Outbox(
            Long aggregateRootId,
            EventType eventType,
            Object eventData,
            EventStatus eventStatus,
            LocalDateTime createdAt
    ) {
        this.aggregateRootId = aggregateRootId;
        this.eventType = eventType;
        this.eventData = eventData;
        this.eventStatus = eventStatus;
        this.createdAt = createdAt;
    }

    public static Outbox create(OutboxEvent outboxEvent) {
        return new Outbox(
                outboxEvent.getAggregateRootId(),
                outboxEvent.getEventType(),
                outboxEvent,
                EventStatus.WAITING,
                LocalDateTime.now()
        );
    }

    public Long getId() {
        return id;
    }

    public EventType getEventType() {
        return eventType;
    }

    public Object getEventData() {
        return eventData;
    }

    public void complete() {
        this.eventStatus = EventStatus.COMPLETED;
        this.completedAt = LocalDateTime.now();
    }
}
