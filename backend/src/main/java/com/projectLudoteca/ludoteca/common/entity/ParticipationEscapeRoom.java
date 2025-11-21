package com.projectLudoteca.ludoteca.common.entity;

import com.projectLudoteca.ludoteca.common.entity.id.GameEventId;
import com.projectLudoteca.ludoteca.common.entity.id.ParticipationEscapeRoomId;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "participation_escape_room")
public class ParticipationEscapeRoom {

    @EmbeddedId
    private ParticipationEscapeRoomId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("escapeRoomSessionId")
    @JoinColumn(name = "escape_room_session_id")
    private EscapeRoomSession escapeRoomSession;

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

    public ParticipationEscapeRoom() {}

    public ParticipationEscapeRoom(EscapeRoomSession escapeRoomSession, User user) {
        this.escapeRoomSession = escapeRoomSession;
        this.user = user;
        this.id = new ParticipationEscapeRoomId(user.getId(), escapeRoomSession.getId());
    }

}
