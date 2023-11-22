package com.toy.outbox.command.application;

import com.toy.outbox.command.domain.Outbox;
import com.toy.outbox.command.domain.OutboxRepository;
import com.toy.outbox.command.domain.event.OutboxEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OutboxService {
    private final OutboxRepository outboxRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public OutboxService(OutboxRepository outboxRepository, ApplicationEventPublisher eventPublisher) {
        this.outboxRepository = outboxRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public void register(OutboxEvent outboxEvent) {
        Outbox outbox = Outbox.create(outboxEvent);
        outboxRepository.save(outbox);
        outboxEvent.setId(outbox.getId());
        eventPublisher.publishEvent(outboxEvent);
    }

    @Transactional
    public void complete(OutboxEvent outboxEvent) {
        Outbox outbox = outboxRepository.findById(outboxEvent.getId()).orElseThrow();
        outbox.complete();
    }
}
