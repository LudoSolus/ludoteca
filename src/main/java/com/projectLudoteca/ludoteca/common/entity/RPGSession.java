package com.projectLudoteca.ludoteca.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "rpg_session")
public class RPGSession implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rpg_id", nullable = false)
    private OneShotRPG rpg;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "participation_rpg_id", nullable = false)
    private ParticipationRPG participationRPG;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(name = "start_date_time")
    private LocalDateTime startDateTime;

    @Column(name = "finish_date_time")
    private LocalDateTime finishDateTime;

    @Column(name = "link_evaluation")
    private String linkEvaluation;

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

    public RPGSession() {}

    public RPGSession(LocalDateTime startDateTime, LocalDateTime finishDateTime, OneShotRPG rpg, ParticipationRPG participationRPG, Event event, String linkEvaluation) {
        this.startDateTime = startDateTime;
        this.finishDateTime = finishDateTime;
        this.rpg = rpg;
        this.participationRPG = participationRPG;
        this.event = event;
        this.linkEvaluation = linkEvaluation;
    }

}
