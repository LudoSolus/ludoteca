package com.projectLudoteca.ludoteca.common.repository;

import com.projectLudoteca.ludoteca.common.entity.Character;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CharacterRepository extends JpaRepository<Character, UUID> {
    
    Optional<Character> findByIdAndRemovedFalse(UUID id);
    
    List<Character> findByUserIdAndRemovedFalse(UUID userId);
    
    List<Character> findByRemovedFalse();
}
