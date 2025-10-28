package com.projectLudoteca.ludoteca.common.repository;

import java.util.UUID;

import com.projectLudoteca.ludoteca.common.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, UUID> {
    boolean existsByBarcode(Integer barcode);
}
