package com.projectLudoteca.ludoteca.command.registerParticipationEvent;

import java.util.UUID;

public record CreateParticipationEventCommand(String userPublicId, UUID eventId) {
}
