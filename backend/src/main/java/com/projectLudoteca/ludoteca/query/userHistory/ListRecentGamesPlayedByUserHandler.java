package com.projectLudoteca.ludoteca.query.userHistory;

import com.projectLudoteca.ludoteca.common.entity.Loan;
import com.projectLudoteca.ludoteca.common.entity.User;
import com.projectLudoteca.ludoteca.common.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListRecentGamesPlayedByUserHandler {

    private final LoanRepository loanRepository;

    public ListRecentGamesPlayedByUserHandler(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public List<RecentGamePlayedView> handle(User user) {
        System.out.println("DEBUG: User ID = " + user.getId());
        System.out.println("DEBUG: User Name = " + user.getName());
        
        List<Loan> recentLoans = loanRepository.findRecentGamesByUserId(user.getId());
        
        return recentLoans.stream()
                .map(loan -> new RecentGamePlayedView(
                        loan.getGame().getId(),
                        loan.getGame().getBarcode(),
                        loan.getGame().getTitle(),
                        loan.getGame().getCategory().name(),
                        loan.getGame().getMinPlayers(),
                        loan.getGame().getMaxPlayers()))
                .toList();
    }
}
