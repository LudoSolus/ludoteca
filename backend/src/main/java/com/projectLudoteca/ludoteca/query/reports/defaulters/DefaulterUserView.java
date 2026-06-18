package com.projectLudoteca.ludoteca.query.reports.defaulters;

import java.time.LocalDateTime;
import java.util.UUID;

public record DefaulterUserView(
        UUID userId,
        String userName,
        String userEmail,
        String gameTitle,
        LocalDateTime expectedReturnDate,
        long daysLate
) {
}