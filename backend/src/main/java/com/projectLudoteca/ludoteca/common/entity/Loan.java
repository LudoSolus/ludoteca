package com.projectLudoteca.ludoteca.common.entity;

import com.projectLudoteca.ludoteca.common.enums.GameStatus;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Loan implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @CreatedDate
    @Column(name = "date_loan", updatable = false)
    private LocalDateTime dateLoan;

    @Column(name = "date_return", updatable = false)
    private LocalDateTime dateReturn;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private GameStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(nullable = false)
    private Boolean removed = false;

    public Loan() {}

    public Loan(LocalDateTime dateReturn, GameStatus status, User user, Game game) {
        this.dateReturn = dateReturn;
        this.status = status;
        this.user = user;
        this.game = game;
    }

    public UUID getId() { return id; }

    public LocalDateTime getDateLoan() { return dateLoan; }

    public LocalDateTime getDateReturn() { return dateReturn; }

    public void setDateReturn(LocalDateTime dateReturn) { this.dateReturn = dateReturn; }

    public GameStatus getStatus() { return status; }

    public void setStatus(GameStatus status) { this.status = status; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public Game getGame() { return game; }

    public void setGame(Game game) { this.game = game; }

    public Boolean getRemoved() { return removed; }

    public void setRemoved(Boolean removed) { this.removed = removed; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public LocalDateTime getDeletedAt() { return deletedAt; }

}
