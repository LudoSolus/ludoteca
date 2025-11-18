package com.projectLudoteca.ludoteca.query.eventDetails;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record GetEventDetailsView(String name,
                                  LocalDateTime startDate,
                                  LocalDateTime finalDate,
                                  String street,
                                  String number,
                                  String supplement,
                                  String neighborhood,
                                  String city,
                                  String state,
                                  String zipCode,
                                  List<ListGamesView> listGames) {
    public record ListGamesView(
            String nameGame,
            Boolean isAvailable,
            String category,
            Integer minPlayers,
            Integer maxPlayers
    ) {}
}