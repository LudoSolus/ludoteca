package com.projectLudoteca.ludoteca.common.entity.id;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
public class ParticipationEscapeRoomId implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private UUID userId;
    private UUID escapeRoomSessionId;

    public ParticipationEscapeRoomId() {}

    public ParticipationEscapeRoomId(UUID userId, UUID escapeRoomSessionId) {
        this.userId = userId;
        this.escapeRoomSessionId = escapeRoomSessionId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ParticipationEscapeRoomId that = (ParticipationEscapeRoomId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(escapeRoomSessionId, that.escapeRoomSessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, escapeRoomSessionId);
    }
}
