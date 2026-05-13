package com.projectLudoteca.ludoteca.common.repository;

import com.projectLudoteca.ludoteca.common.entity.Event;
import com.projectLudoteca.ludoteca.common.entity.ParticipationEvent;
import com.projectLudoteca.ludoteca.common.entity.User;
import com.projectLudoteca.ludoteca.common.entity.id.ParticipationEventId;
import com.projectLudoteca.ludoteca.query.dashboard.ParticipantsByEventData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParticipationEventRepository extends JpaRepository<ParticipationEvent, ParticipationEventId> {
    boolean existsByEventAndUser(Event event, User user);

    @Query("SELECT new com.projectLudoteca.ludoteca.query.dashboard.ParticipantsByEventData(" +
           "e.name, COUNT(pe)) FROM ParticipationEvent pe " +
           "JOIN pe.event e " +
           "WHERE pe.removed = false AND e.removed = false " +
           "GROUP BY e.id, e.name " +
           "ORDER BY COUNT(pe) DESC")
    List<ParticipantsByEventData> countParticipantsByEvent();
}
