package com.projectLudoteca.ludoteca.common.repository;

import com.projectLudoteca.ludoteca.common.entity.Event;
import com.projectLudoteca.ludoteca.common.entity.GameEvent;
import com.projectLudoteca.ludoteca.common.entity.id.GameEventId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GameEventRepository extends JpaRepository<GameEvent, GameEventId> {

    @Modifying
    @Transactional
    @Query("DELETE FROM GameEvent ge WHERE ge.event = :event")
    void deleteByEvent(@Param("event") Event event);
}
