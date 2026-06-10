package com.projectLudoteca.ludoteca.common.repository;

import com.projectLudoteca.ludoteca.common.entity.Loan;
import com.projectLudoteca.ludoteca.query.dashboard.MostPlayedGamesData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoanRepository extends JpaRepository<Loan, UUID> {

    Optional<Loan> findByGameIdAndDateReturnIsNullAndRemovedFalse(UUID gameId);

    boolean existsByGameIdAndDateReturnIsNullAndRemovedFalse(UUID gameId);

    @Query("SELECT l FROM Loan l " +
           "WHERE l.user.id = :userId AND l.removed = false " +
           "ORDER BY l.dateLoan DESC")
    List<Loan> findRecentGamesByUserId(UUID userId);

    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM Loan l " +
           "WHERE l.user.id = :userId AND l.status = com.projectLudoteca.ludoteca.common.enums.GameStatus.BORROWED " +
           "AND l.removed = false")
    boolean hasActiveLoansByUserId(UUID userId);

    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM Loan l " +
           "WHERE l.event.id = :eventId AND l.status = com.projectLudoteca.ludoteca.common.enums.GameStatus.BORROWED " +
           "AND l.removed = false")
    boolean hasActiveLoansByEventId(UUID eventId);

    @Query("SELECT new com.projectLudoteca.ludoteca.query.dashboard.MostPlayedGamesData(" +
           "g.title, COUNT(l)) FROM Loan l " +
           "JOIN l.game g " +
           "WHERE l.removed = false AND g.removed = false " +
           "GROUP BY g.id, g.title " +
           "ORDER BY COUNT(l) DESC")
    List<MostPlayedGamesData> countMostPlayedGames();
}

