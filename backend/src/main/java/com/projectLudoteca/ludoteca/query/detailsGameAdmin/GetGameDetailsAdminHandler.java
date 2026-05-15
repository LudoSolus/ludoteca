package com.projectLudoteca.ludoteca.query.detailsGameAdmin;

import com.projectLudoteca.ludoteca.common.entity.Game;
import com.projectLudoteca.ludoteca.common.exception.BusinessException;
import com.projectLudoteca.ludoteca.common.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GetGameDetailsAdminHandler {

    private final GameRepository gameRepository;

    public GetGameDetailsAdminHandler(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public GetGameDetailsAdminView handle(String id) {

        UUID gameId;

        try{
            gameId = UUID.fromString(id);
        } catch (RuntimeException e) {
            throw new BusinessException("Id de jogo inválido!");
        }

        Game game = gameRepository.findByIdAndRemovedFalse(gameId)
                .orElseThrow(() -> new NoSuchElementException("Jogo não encontrado."));

        List<GetGameDetailsAdminView.LoanHistoryView> loanHistory = game.getLoans().stream()
                .map(loan -> new GetGameDetailsAdminView.LoanHistoryView(
                        loan.getId(),
                        loan.getUser().getId(),
                        loan.getUser().getEmail(),
                        loan.getDateLoan()
                ))
                .collect(Collectors.toList());

        return new GetGameDetailsAdminView(
                game.getBarcode(),
                game.getId(),
                game.getTitle(),
                game.getDescription(),
                game.getCategory().name(),
                game.getMinPlayers(),
                game.getMaxPlayers(),
                game.getLinkInstructionManual(),
                game.getLinkVideoTutorial(),
                loanHistory
        );
    }
}
