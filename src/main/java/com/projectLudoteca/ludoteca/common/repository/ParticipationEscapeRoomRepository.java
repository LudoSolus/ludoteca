package com.projectLudoteca.ludoteca.common.repository;

import com.projectLudoteca.ludoteca.common.entity.ParticipationEscapeRoom;
import com.projectLudoteca.ludoteca.common.entity.id.ParticipationEscapeRoomId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipationEscapeRoomRepository extends JpaRepository<ParticipationEscapeRoom, ParticipationEscapeRoomId> {
}
