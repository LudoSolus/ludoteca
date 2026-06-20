package com.projectLudoteca.ludoteca.query.listAllGamesForAdmin;

import java.util.UUID;

public record GetAllGamesForAdminView(
        UUID id,
        Integer barcode,
        String name,
        String category,
        Integer minParticipants,
        Integer maxParticipants) {
}
