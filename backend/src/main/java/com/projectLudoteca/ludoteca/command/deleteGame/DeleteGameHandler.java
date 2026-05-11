package com.projectLudoteca.ludoteca.command.deleteGame;

import com.projectLudoteca.ludoteca.common.entity.Game;
import com.projectLudoteca.ludoteca.common.exception.BusinessException;
import com.projectLudoteca.ludoteca.common.repository.GameRepository;
import com.projectLudoteca.ludoteca.common.repository.LoanRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class DeleteGameHandler {

    private final GameRepository gameRepository;
    private final LoanRepository loanRepository;

    public DeleteGameHandler(GameRepository gameRepository, LoanRepository loanRepository) {
        this.gameRepository = gameRepository;
        this.loanRepository = loanRepository;
    }

    @Transactional
    public String handle(String id) {

        // ✅ Validação 1: Verificar se o ID foi informado
        if (id == null || id.isEmpty()) {
            throw new BusinessException("Não é possível fazer exclusão porque o id do jogo não foi informado.");
        }

        // ✅ Validação 2: Validar se o ID é válido
        UUID gameId;
        try {
            gameId = UUID.fromString(id);
        } catch (RuntimeException e) {
            throw new BusinessException("Não é possível fazer exclusão porque o id do jogo é inválido.");
        }

        // ✅ Validação 3: Verificar se o jogo existe e não foi removido
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new NoSuchElementException("Jogo não encontrado para o id informado."));

        // ✅ Validação 4: Verificar se o jogo já foi removido
        if (game.getRemoved()) {
            throw new BusinessException("O jogo já foi removido do sistema.");
        }

        // ✅ Validação 5: Verificar se o jogo está emprestado
        if (loanRepository.existsByGameIdAndDateReturnIsNullAndRemovedFalse(gameId)) {
            throw new BusinessException("Não é possível remover o jogo porque ele está emprestado no momento.");
        }

        // ✅ Executar exclusão lógica
        game.setRemoved(true);
        game.setDeletedAt(LocalDateTime.now());

        gameRepository.save(game);

        return "Jogo removido com sucesso.";
    }
}
