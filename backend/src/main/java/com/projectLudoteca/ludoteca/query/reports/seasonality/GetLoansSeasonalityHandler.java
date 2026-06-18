package com.projectLudoteca.ludoteca.query.reports.seasonality;

import com.projectLudoteca.ludoteca.common.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetLoansSeasonalityHandler {

    private final LoanRepository loanRepository;

    public GetLoansSeasonalityHandler(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public List<LoansSeasonalityView> handle() {
        List<Object[]> rawData = loanRepository.findLoansSeasonality();

        return rawData.stream()
                .map(row -> new LoansSeasonalityView(
                        ((Number) row[0]).intValue(),    // Extrai o Mês (Index 0)
                        ((Number) row[1]).intValue(),    // Extrai o Ano (Index 1)
                        ((Number) row[2]).longValue()    // Extrai o Total (Index 2)
                ))
                .toList();
    }
}