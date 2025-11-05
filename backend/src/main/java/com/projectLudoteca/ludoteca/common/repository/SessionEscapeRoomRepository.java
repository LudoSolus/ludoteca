package com.projectLudoteca.ludoteca.common.repository;

import com.projectLudoteca.ludoteca.common.entity.EscapeRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SessionEscapeRoomRepository extends JpaRepository<EscapeRoom, UUID> {
}
