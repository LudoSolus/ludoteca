package com.projectLudoteca.ludoteca.command.loanGameInEvent;

import java.util.UUID;

public record LoanGameInEventCommand (String userPublicId, UUID gameId, UUID eventId) {
}
