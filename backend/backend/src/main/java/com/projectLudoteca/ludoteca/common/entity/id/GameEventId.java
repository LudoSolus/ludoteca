package com.projectLudoteca.ludoteca.common.entity.id;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
public class GameEventId implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private UUID gameId;
    private UUID eventId;

    public GameEventId() {}

    public GameEventId(UUID gameId, UUID eventId) {
        this.gameId = gameId;
        this.eventId = eventId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GameEventId that = (GameEventId) o;
        return Objects.equals(gameId, that.gameId) && Objects.equals(eventId, that.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, eventId);
    }
}
