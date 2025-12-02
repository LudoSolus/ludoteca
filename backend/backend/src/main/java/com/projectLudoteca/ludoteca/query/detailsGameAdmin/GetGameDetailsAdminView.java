package com.projectLudoteca.ludoteca.query.detailsGameAdmin;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record GetGameDetailsAdminView(Integer barcode,
                                      UUID id,
                                      String title,
                                      String description,
                                      String category,
                                      Integer minPlayers,
                                      Integer maxPlayers,
                                      String linkInstructionManual,
                                      String linkVideoTutorial,
                                      List<LoanHistoryView> loanHistory
) {
    public record LoanHistoryView(
            UUID loanId,
            UUID userId,
            String userEmail,
            LocalDateTime loanDate
    ) {}
}
