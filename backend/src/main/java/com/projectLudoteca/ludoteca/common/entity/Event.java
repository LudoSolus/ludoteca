package com.projectLudoteca.ludoteca.common.entity;

import com.projectLudoteca.ludoteca.common.enums.EventStatus;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
public class Event implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(name = "start_date", updatable = false, nullable = false)
    private LocalDateTime startDate;

    @Column(name = "final_date", nullable = false)
    private LocalDateTime finalDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EventStatus status;

    @Column(length = 100, nullable = false)
    private String street;

    @Column(length = 10, nullable = false)
    private String number;

    @Column(length = 50)
    private String supplement;

    @Column(length = 50, nullable = false)
    private String neighborhood;

    @Column(length = 50, nullable = false)
    private String city;

    @Column(length = 2, nullable = false)
    private String state;

    @Column(length = 9, nullable = false)
    private String zipCode;

    @Column(name = "link_evaluation")
    private String linkEvaluation;

    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ParticipationEvent> participationEvents = new ArrayList<>();

    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Loan> loans = new ArrayList<>();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<EscapeRoomSession> sessionsEscapeRoom = new ArrayList<>();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RPGSession> sessionsRPG = new ArrayList<>();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<GameEvent> gamesEvent = new ArrayList<>();

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

    public Event() {}

    public Event (String name, String description, LocalDateTime finishDate, EventStatus status, String street, String number, String supplement, String neighborhood, String city, String state, String zipCode, String linkEvaluation ) {
        this.name = name;
        this.description = description;
        this.finalDate = finishDate;
        this.status = status;
        this.street = street;
        this.number = number;
        this.supplement = supplement;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.linkEvaluation = linkEvaluation;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setFinalDate(LocalDateTime finalDate) {
        this.finalDate = finalDate;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public void setLinkEvaluation(String linkEvaluation) {
        this.linkEvaluation = linkEvaluation;
    }

    public List<ParticipationEvent> getParticipationEvents() { return participationEvents; }

    public List<Loan> getLoans() { return loans; }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public void setRemoved(Boolean removed) {
        this.removed = removed;
    }
}
