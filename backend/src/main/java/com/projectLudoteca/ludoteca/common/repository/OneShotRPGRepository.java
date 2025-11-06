package com.projectLudoteca.ludoteca.common.repository;

import com.projectLudoteca.ludoteca.common.entity.ParticipationEscapeRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OneShotRPGRepository extends JpaRepository<ParticipationEscapeRoom, UUID> {
}
