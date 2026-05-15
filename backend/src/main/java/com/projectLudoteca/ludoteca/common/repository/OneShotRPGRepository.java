package com.projectLudoteca.ludoteca.common.repository;

import com.projectLudoteca.ludoteca.common.entity.OneShotRPG;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OneShotRPGRepository extends JpaRepository<OneShotRPG, UUID> {
    
    Optional<OneShotRPG> findByIdAndRemovedFalse(UUID id);
    
    List<OneShotRPG> findByRemovedFalse();
}
