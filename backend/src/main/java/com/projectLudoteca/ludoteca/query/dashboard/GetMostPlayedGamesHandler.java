package com.projectLudoteca.ludoteca.query.dashboard;

import com.projectLudoteca.ludoteca.common.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetMostPlayedGamesHandler {

    private final LoanRepository loanRepository;

    public GetMostPlayedGamesHandler(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public MostPlayedGamesResponse handle() {
        List<MostPlayedGamesData> data = loanRepository.countMostPlayedGames();
        return new MostPlayedGamesResponse(data);
    }
}
