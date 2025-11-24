package com.projectLudoteca.ludoteca.command.returnedGame;

import com.projectLudoteca.ludoteca.common.entity.Game;
import com.projectLudoteca.ludoteca.common.entity.Loan;
import com.projectLudoteca.ludoteca.common.enums.GameStatus;
import com.projectLudoteca.ludoteca.common.repository.GameRepository;
import com.projectLudoteca.ludoteca.common.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ReturnGameHandler {

    private final GameRepository gameRepository;
    private final LoanRepository loanRepository;

    public ReturnGameHandler(GameRepository gameRepository, LoanRepository loanRepository) {
        this.gameRepository = gameRepository;
        this.loanRepository = loanRepository;
    }

    public String handle(UUID gameId) {

        Game game = gameRepository.findByIdAndRemovedFalse(gameId)
                .orElseThrow(() -> new RuntimeException("Jogo não encontrado ou removido."));

        Loan activeLoan = loanRepository.findByGameIdAndDateReturnIsNullAndRemovedFalse(gameId)
                .orElseThrow(() -> new RuntimeException("Este jogo não está emprestado no momento."));

        activeLoan.setDateReturn(LocalDateTime.now());
        activeLoan.setStatus(GameStatus.RETURNED);

        game.setIsAvailable(true);

        loanRepository.save(activeLoan);
        gameRepository.save(game);

        return "Jogo devolvido com sucesso.";
    }

}
