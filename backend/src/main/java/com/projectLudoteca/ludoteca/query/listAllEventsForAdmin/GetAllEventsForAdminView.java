package com.projectLudoteca.ludoteca.query.listAllEventsForAdmin;

import java.time.LocalDateTime;
import java.util.UUID;

import com.projectLudoteca.ludoteca.common.enums.EventStatus;

public record GetAllEventsForAdminView(UUID id,
        String name,
        LocalDateTime startDate,
        String finalDate,
        EventStatus status,
        String street,
        String number,
        String neighborhood,
        String city,
        String state,
        String zipCode,
        Boolean hasBoardGames,
        Boolean hasRpg,
        Boolean hasEscapeRoom) {
}
