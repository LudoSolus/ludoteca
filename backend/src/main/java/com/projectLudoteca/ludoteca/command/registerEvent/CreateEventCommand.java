package com.projectLudoteca.ludoteca.command.registerEvent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record CreateEventCommand(String name,
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
                                 boolean hasBoardGames,
                                 boolean hasRpg,
                                 boolean hasEscapeRoom,
                                 List<UUID> gamesIds) {
}
