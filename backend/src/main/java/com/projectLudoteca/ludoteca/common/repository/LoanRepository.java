package com.projectLudoteca.ludoteca.common.repository;

import com.projectLudoteca.ludoteca.common.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LoanRepository extends JpaRepository<Loan, UUID> {

    Optional<Loan> findByGameIdAndDateReturnIsNullAndRemovedFalse(UUID gameId);

    boolean existsByGameIdAndDateReturnIsNullAndRemovedFalse(UUID gameId);
}

