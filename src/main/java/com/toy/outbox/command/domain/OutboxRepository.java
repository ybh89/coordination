package com.toy.outbox.command.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutboxRepository extends JpaRepository<Outbox, Long> {
    List<Outbox> findAllByEventStatus(EventStatus waiting);
}
