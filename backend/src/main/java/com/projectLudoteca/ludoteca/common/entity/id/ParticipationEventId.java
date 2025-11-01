package com.projectLudoteca.ludoteca.common.entity.id;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class ParticipationEventId implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private UUID userId;
    private UUID eventId;

    public ParticipationEventId() {}

    public ParticipationEventId(UUID userId, UUID eventId) {
        this.userId = userId;
        this.eventId = eventId;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getEventId() {
        return eventId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ParticipationEventId that = (ParticipationEventId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(eventId, that.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, eventId);
    }
}
