package com.projectLudoteca.ludoteca.common.repository;

import com.projectLudoteca.ludoteca.common.entity.EscapeRoomSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RPGSessionRepository extends JpaRepository<EscapeRoomSession, UUID> {
}
