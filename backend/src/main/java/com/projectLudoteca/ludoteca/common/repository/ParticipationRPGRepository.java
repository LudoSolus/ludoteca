package com.projectLudoteca.ludoteca.common.repository;

import com.projectLudoteca.ludoteca.common.entity.ParticipationRPG;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ParticipationRPGRepository extends JpaRepository<ParticipationRPG, UUID> {
}
