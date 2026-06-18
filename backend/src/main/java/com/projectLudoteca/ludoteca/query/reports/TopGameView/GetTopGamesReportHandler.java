package com.projectLudoteca.ludoteca.query.reports.topGames;

import com.projectLudoteca.ludoteca.common.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetTopGamesReportHandler {

    private final LoanRepository loanRepository;

    public GetTopGamesReportHandler(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public List<TopGameView> handle() {
        return loanRepository.findTopGamesReport();
    }
}