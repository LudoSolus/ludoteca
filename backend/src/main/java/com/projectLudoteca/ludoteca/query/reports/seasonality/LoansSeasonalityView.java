package com.projectLudoteca.ludoteca.query.reports.seasonality;

public record LoansSeasonalityView(
        int month,
        int year,
        Long totalLoans
) {
}