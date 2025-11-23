package com.projectLudoteca.ludoteca.command.updateEvent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record UpdateEventCommand(String name,
                                 String description,
                                 LocalDateTime startDate,
                                 LocalDateTime finalDate,
                                 String street,
                                 String number,
                                 String supplement,
                                 String neighborhood,
                                 String city,
                                 String state,
                                 String zipCode,
                                 Boolean hasBoardGames,
                                 Boolean hasRpg,
                                 Boolean hasEscapeRoom,
                                 List<UUID> gamesIds) {
}
