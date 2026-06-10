package com.projectLudoteca.ludoteca.query.userHistory;

import java.time.LocalDateTime;
import java.util.UUID;

public record RecentGamePlayedView(UUID gameId,
                                   String gameName,
                                   LocalDateTime playedAt) {
}
