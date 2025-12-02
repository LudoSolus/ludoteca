package com.projectLudoteca.ludoteca.common.repository;

import com.projectLudoteca.ludoteca.common.entity.Event;
import com.projectLudoteca.ludoteca.common.entity.ParticipationEvent;
import com.projectLudoteca.ludoteca.common.entity.User;
import com.projectLudoteca.ludoteca.common.entity.id.ParticipationEventId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipationEventRepository extends JpaRepository<ParticipationEvent, ParticipationEventId> {
    boolean existsByEventAndUser(Event event, User user);
}
