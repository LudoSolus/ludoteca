package com.projectLudoteca.ludoteca.common.entity;

import com.projectLudoteca.ludoteca.common.enums.AgeRange;
import com.projectLudoteca.ludoteca.common.enums.EscapeRoomDifficulty;
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
@Table(name = "escape_room")
public class EscapeRoom implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String theme;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty", nullable = false)
    private EscapeRoomDifficulty difficulty;

    @Column(nullable = false)
    private Integer duration;

    @Column(nullable = false)
    private Integer maxCapacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "age_range", nullable = false)
    private AgeRange ageRange;

    private String sensitiveTopics;

    @OneToMany(mappedBy = "escapeRoom", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<EscapeRoomSession> sessionsEscapeRoom = new ArrayList<>();

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

    public EscapeRoom() {}

    public EscapeRoom(String theme, EscapeRoomDifficulty difficulty, Integer duration, Integer maxCapacity, AgeRange ageRange, String sensitiveTopics ) {
        this.theme = theme;
        this.difficulty = difficulty;
        this.duration = duration;
        this.maxCapacity = maxCapacity;
        this.ageRange = ageRange;
        this.sensitiveTopics = sensitiveTopics;
    }

}
