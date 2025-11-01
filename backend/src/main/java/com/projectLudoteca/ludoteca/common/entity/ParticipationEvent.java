package com.projectLudoteca.ludoteca.common.entity;

import com.projectLudoteca.ludoteca.common.entity.id.GameEventId;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "participation_event")
public class ParticipationEvent {

    @EmbeddedId
    private GameEventId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private Game game;

    @ManyToOne
    @MapsId("eventId")
    @JoinColumn(name = "event_id")
    private User user;

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

    public ParticipationEvent() {}

    public ParticipationEvent(Game game, User user) {
        this.game = game;
        this.user = user;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
