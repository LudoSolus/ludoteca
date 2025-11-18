package com.projectLudoteca.ludoteca.common.entity;

import com.projectLudoteca.ludoteca.common.enums.ResultEscapeRoomSession;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "escape_room_session")
public class EscapeRoomSession implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "start_date_time")
    private LocalDateTime startDateTime;

    @Column(name = "finish_date_time")
    private LocalDateTime finishDateTime;

    @Column(name = "conclusion_time-minutes")
    private Integer conclusionTimeMinutes;

    @Enumerated(EnumType.STRING)
    @Column(name = "result", nullable = false)
    private ResultEscapeRoomSession result;

    @Column(name = "link_evaluation")
    private String linkEvaluation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "presenter_id", nullable = false)
    private User presenter;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "escape_room_id", nullable = false)
    private EscapeRoom escapeRoom;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id")
    private Event event;

    @OneToMany(mappedBy = "escapeRoomSession", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ParticipationEscapeRoom> participationsEscapeRoom = new ArrayList<>();

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

    public EscapeRoomSession() {}

    public EscapeRoomSession(LocalDateTime startDateTime, LocalDateTime finishDateTime, Integer conclusionTimeMinutes, ResultEscapeRoomSession result, String linkEvaluation, User presenter, EscapeRoom escapeRoom, Event event) {
        this.startDateTime = startDateTime;
        this.finishDateTime = finishDateTime;
        this.conclusionTimeMinutes = conclusionTimeMinutes;
        this.result = result;
        this.linkEvaluation = linkEvaluation;
        this.presenter = presenter;
        this.escapeRoom = escapeRoom;
        this.event = event;
    }

}
