package com.projectLudoteca.ludoteca.query.reports.topGames;

import java.util.UUID;

public record TopGameView(
        UUID gameId,
        String title,
        Long totalLoans
) {
}