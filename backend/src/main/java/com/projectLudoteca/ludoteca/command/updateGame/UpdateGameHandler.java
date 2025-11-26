package com.projectLudoteca.ludoteca.command.updateGame;

import com.projectLudoteca.ludoteca.common.entity.Game;
import com.projectLudoteca.ludoteca.common.enums.GameCategory;
import com.projectLudoteca.ludoteca.common.exception.BusinessException;
import com.projectLudoteca.ludoteca.common.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class UpdateGameHandler {
    private final GameRepository gameRepository;

    @Autowired
    public UpdateGameHandler(GameRepository repository) {
        this.gameRepository = repository;
    }

    public String handle(String id, UpdateGameCommand command) {

        if (id == null ||  id.isEmpty()) {
            throw new BusinessException("Não é possível fazer alteração porque o id do game não foi informado.");
        }
        UUID gameId;
        try {
            gameId = UUID.fromString(id);
        } catch (RuntimeException e) {
            throw new BusinessException("Não é possível fazer alteração porque o id do game é inválido.");
        }


        Game game = gameRepository.findById(gameId).orElseThrow(() -> new NoSuchElementException("Jogo não encontrado para o id informado."));

        boolean allNullOrBlank =
                (command.barcode() == null) &&
                        (command.title() == null || command.title().isBlank()) &&
                        (command.category() == null || command.category().isBlank()) &&
                        (command.description() == null || command.description().isBlank()) &&
                        (command.minPlayers() == null) &&
                        (command.maxPlayers() == null) &&
                        (command.linkInstructionManual() == null) &&
                        (command.linkVideoTutorial() == null);

        if (allNullOrBlank) {
            throw new BusinessException("Não é possível fazer alteração porque todos os campos estão nulos");
        }

        if (command.minPlayers() != null && command.maxPlayers() != null) {
            if (command.maxPlayers() < command.minPlayers()) {
                throw new BusinessException("O número máximo de jogadores não pode ser menor que o mínimo.");
            }
            if (command.maxPlayers() <= 0) {
                throw new BusinessException("O número máximo de jogadores não pode ser menor ou igual a 0");
            }
            if (command.minPlayers() <= 0) {
                throw new BusinessException("O número mínimo de jogadores não pode ser menor ou igual a 0");
            }
        }

        if (command.linkInstructionManual() != null && !isValidUrl(command.linkInstructionManual())) {
            throw new BusinessException("O link do manual de instruções é inválido.");
        }

        if (command.linkVideoTutorial() != null && !isValidUrl(command.linkVideoTutorial())) {
            throw new BusinessException("O link do vídeo tutorial é inválido.");
        }

        if (command.barcode() != null) {
            if (gameRepository.existsByBarcode(command.barcode())) {
                throw new BusinessException("Já existe um jogo com esse código de barras cadastrado no sistema.");
            }
            game.setBarcode(command.barcode());
        }

        if (command.title() != null) {
            game.setTitle(command.title());
        }
        if (command.category() != null) {
            game.setCategory(GameCategory.valueOf(command.category()));
        }
        if (command.description() != null) {
            game.setDescription(command.description());
        }
        if (command.minPlayers() != null) {
            game.setMinPlayers(command.minPlayers());
        }
        if (command.maxPlayers() != null) {
            game.setMaxPlayers(command.maxPlayers());
        }
        if (command.linkInstructionManual() != null) {
            game.setLinkInstructionManual(command.linkInstructionManual());
        }
        if (command.linkVideoTutorial() != null) {
            game.setLinkVideoTutorial(command.linkVideoTutorial());
        }

        gameRepository.save(game);

        return "Jogo alterado com sucesso!";
    }

    private boolean isValidUrl(String url) {
        try {
            new java.net.URL(url);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
