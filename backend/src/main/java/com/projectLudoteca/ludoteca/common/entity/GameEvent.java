package com.projectLudoteca.ludoteca.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "game_event")
public class GameEvent {

    @EmbeddedId
    private GameEventId id;

    @ManyToOne
    @MapsId("gameId") // mapeia o campo studentId da chave composta
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @MapsId("eventId")
    @JoinColumn(name = "event_id")
    private Event event;

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

    public GameEvent() {}

    public GameEvent(Game game, Event event) {
        this.game = game;
        this.event = event;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

}
