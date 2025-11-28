package com.projectLudoteca.ludoteca.query.eventDetails;

import com.projectLudoteca.ludoteca.common.enums.EventStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record GetEventDetailsView(String name,
                                  EventStatus status,
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
    public record ListGamesView( UUID id,
            Integer barcode,
            String nameGame,
            Boolean isAvailable,
            String category,
            Integer minPlayers,
            Integer maxPlayers
    ) {}
}