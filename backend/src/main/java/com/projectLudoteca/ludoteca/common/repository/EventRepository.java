package com.projectLudoteca.ludoteca.common.repository;

import com.projectLudoteca.ludoteca.common.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
}
