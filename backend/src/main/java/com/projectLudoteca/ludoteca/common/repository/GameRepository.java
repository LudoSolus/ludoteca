package com.projectLudoteca.ludoteca.common.repository;

import java.util.Optional;
import java.util.UUID;

import com.projectLudoteca.ludoteca.common.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, UUID> {
    boolean existsByBarcodeAndRemovedFalse(Integer barcode);

    boolean existsByBarcodeAndIdNot(Integer barcode, UUID id);

    Optional<Game> findByIdAndRemovedFalse(UUID id);

    Optional<Game> findByBarcodeAndRemovedFalse(Integer barcode);
}
