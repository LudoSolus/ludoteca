package com.projectLudoteca.ludoteca.common.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.projectLudoteca.ludoteca.common.enums.GameCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Game implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(name = "barcode", unique = true)
    private Integer barcode;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private GameCategory category;

    @Column(length = 1000, nullable = false)
    private String description;

    @Column(name = "min_players", nullable = false)
    private Integer minPlayers;;

    @Column(name = "max_players")
    private Integer maxPlayers;

    @Column(name = "is_available")
    private Boolean isAvailable = true;

    @Column(name = "link_instruction_manual")
    private String linkInstructionManual;

    @Column(name = "link_video_tutorial")
    private String linkVideoTutorial;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Loan> loans = new ArrayList<>();

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
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

    public Game() {
    }

    public Game(Integer barcode, String title, GameCategory category, String description, Integer minPlayers, Integer maxPlayers, Boolean isAvailable, String linkInstructionManual, String linkVideoTutorial) {
        this.barcode = barcode;
        this.title = title;
        this.category = category;
        this.description = description;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.isAvailable = isAvailable;
        this.linkInstructionManual = linkInstructionManual;
        this.linkVideoTutorial = linkVideoTutorial;
    }

}
