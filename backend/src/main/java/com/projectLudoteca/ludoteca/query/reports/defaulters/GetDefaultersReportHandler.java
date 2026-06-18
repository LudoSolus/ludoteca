package com.projectLudoteca.ludoteca.query.reports.defaulters;

import com.projectLudoteca.ludoteca.common.entity.Loan;
import com.projectLudoteca.ludoteca.common.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class GetDefaultersReportHandler {

    private final LoanRepository loanRepository;

    public GetDefaultersReportHandler(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public List<DefaulterUserView> handle() {
        LocalDateTime now = LocalDateTime.now();
        
        int PRAZO_DIAS = 7; 
        
        LocalDateTime limitDate = now.minusDays(PRAZO_DIAS);

        List<Loan> lateLoans = loanRepository.findDefaulters(limitDate);

        return lateLoans.stream()
                .map(loan -> {
                    LocalDateTime expectedReturnDate = loan.getDateLoan().plusDays(PRAZO_DIAS);
                    
                    long daysLate = ChronoUnit.DAYS.between(expectedReturnDate, now);
                    
                    return new DefaulterUserView(
                            loan.getUser().getId(),
                            loan.getUser().getName(),
                            loan.getUser().getEmail(),
                            loan.getGame().getTitle(),
                            expectedReturnDate,
                            daysLate
                    );
                })
                .toList();
    }
}