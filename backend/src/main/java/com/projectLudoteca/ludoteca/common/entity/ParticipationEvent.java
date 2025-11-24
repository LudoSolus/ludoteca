package com.projectLudoteca.ludoteca.common.entity;

import com.projectLudoteca.ludoteca.common.entity.id.ParticipationEscapeRoomId;
import com.projectLudoteca.ludoteca.common.entity.id.ParticipationEventId;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "participation_event")
public class ParticipationEvent {

    @EmbeddedId
    private ParticipationEventId id;

    @ManyToOne
    @MapsId("eventId")
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
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

    public ParticipationEvent(Event event, User user) {
        this.event = event;
        this.user = user;
        this.id = new ParticipationEventId(user.getId(), event.getId());
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
