package com.projectLudoteca.ludoteca.query.userHistory;

import java.time.LocalDateTime;
import java.util.UUID;

public record RecentGamePlayedView(UUID id,
        Integer barcode,
        String name,
        String category,
        Integer minParticipants,
        Integer maxParticipants) {
}
