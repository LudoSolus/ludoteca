package com.projectLudoteca.ludoteca.common.repository;

import com.projectLudoteca.ludoteca.common.entity.GameEvent;
import com.projectLudoteca.ludoteca.common.entity.id.GameEventId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameEventRepository extends JpaRepository<GameEvent, GameEventId> {
}
