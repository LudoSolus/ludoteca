package com.projectLudoteca.ludoteca.command.loanGameInEvent;

import com.projectLudoteca.ludoteca.common.entity.Event;
import com.projectLudoteca.ludoteca.common.entity.Game;
import com.projectLudoteca.ludoteca.common.entity.Loan;
import com.projectLudoteca.ludoteca.common.entity.User;
import com.projectLudoteca.ludoteca.common.entity.id.GameEventId;
import com.projectLudoteca.ludoteca.common.enums.GameStatus;
import com.projectLudoteca.ludoteca.common.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class LoanGameInEventHandler {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final EventRepository eventRepository;
    private final GameEventRepository gameEventRepository;

    public LoanGameInEventHandler(
            LoanRepository loanRepository,
            UserRepository userRepository,
            GameRepository gameRepository,
            EventRepository eventRepository,
            GameEventRepository gameEventRepository
    ) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.eventRepository = eventRepository;
        this.gameEventRepository = gameEventRepository;
    }

    @Transactional
    public String handle(LoanGameInEventCommand command) {

        User user = userRepository.findByPublicIdAndRemovedFalse(command.userPublicId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        Game game = gameRepository.findByIdAndRemovedFalse(command.gameId())
                .orElseThrow(() -> new RuntimeException("Jogo não encontrado ou removido."));

        Event event = eventRepository.findByIdAndRemovedFalse(command.eventId())
                .orElseThrow(() -> new RuntimeException("Evento não encontrado."));

        if (!gameEventRepository.existsById(new GameEventId(command.gameId(), command.eventId()))) {
            throw new RuntimeException("Este jogo não faz parte deste evento.");
        }

        boolean hasActiveLoan = loanRepository
                .existsByGameIdAndDateReturnIsNullAndRemovedFalse(command.gameId());

        if (hasActiveLoan) {
            throw new RuntimeException("Este jogo já está emprestado.");
        }

        Loan loan = new Loan(
                GameStatus.BORROWED,
                user,
                game,
                event
        );

        game.setIsAvailable(false);

        gameRepository.save(game);
        loanRepository.save(loan);

        return "Jogo emprestado com sucesso!";
    }
}
