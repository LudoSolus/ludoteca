package com.projectLudoteca.ludoteca.command.returnedGame;

import com.projectLudoteca.ludoteca.common.entity.Game;
import com.projectLudoteca.ludoteca.common.entity.Loan;
import com.projectLudoteca.ludoteca.common.entity.User;
import com.projectLudoteca.ludoteca.common.enums.GameStatus;
import com.projectLudoteca.ludoteca.common.exception.BusinessException;
import com.projectLudoteca.ludoteca.common.repository.GameRepository;
import com.projectLudoteca.ludoteca.common.repository.LoanRepository;
import com.projectLudoteca.ludoteca.common.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ReturnGameHandler {

    private final GameRepository gameRepository;
    private final LoanRepository loanRepository;
    private final UserRepository userRepository;

    public ReturnGameHandler(GameRepository gameRepository, LoanRepository loanRepository, UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public String handle(String id) {

        UUID gameId;

        try{
            gameId = UUID.fromString(id);
        } catch (RuntimeException e) {
            throw new BusinessException("Id de jogo inválido!");
        }

        Game game = gameRepository.findByIdAndRemovedFalse(gameId)
                .orElseThrow(() -> new BusinessException("Jogo não encontrado ou removido."));

        Loan activeLoan = loanRepository.findByGameIdAndDateReturnIsNullAndRemovedFalse(gameId)
                .orElseThrow(() -> new BusinessException("Este jogo não está emprestado no momento."));

        UUID userId = activeLoan.getUserId();

        User user = userRepository.findByIdAndRemovedFalse(userId)
                .orElseThrow(() -> new BusinessException("Usuário não existe mais."));

        activeLoan.setDateReturn(LocalDateTime.now());
        activeLoan.setStatus(GameStatus.RETURNED);

        game.setIsAvailable(true);

        loanRepository.save(activeLoan);
        gameRepository.save(game);

        Duration duration = Duration.between(
                activeLoan.getDateLoan(),
                activeLoan.getDateReturn()
        );

        int minutesPlayed = (int) duration.toMinutes();

        int currentMinutes = user.getMinBoardGames() != null ? user.getMinBoardGames() : 0;

        user.setMinBoardGames(currentMinutes + minutesPlayed);

        userRepository.save(user);

        return "Jogo devolvido com sucesso.";
    }

}
