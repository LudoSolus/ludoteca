package com.projectLudoteca.ludoteca.command.registerGame;

import com.projectLudoteca.ludoteca.common.entity.Game;
import com.projectLudoteca.ludoteca.common.enums.GameCategory;
import com.projectLudoteca.ludoteca.common.exception.BusinessException;
import com.projectLudoteca.ludoteca.common.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateGameHandler {

    private GameRepository repository;

    @Autowired
    public CreateGameHandler(GameRepository repository) {
        this.repository = repository;
    }

    public String handle(CreateGameCommand command) {

        if (command.barcode() == null ||
                command.title() == null || command.title().isBlank() ||
                command.category() == null || command.category().isBlank() ||
                command.description() == null || command.description().isBlank() ||
                command.minPlayers() == null || command.maxPlayers() == null) {
            throw new BusinessException("Campos obrigatórios não podem ser nulos ou vazios.");
        }

        if (command.maxPlayers() < command.minPlayers()) {
            throw new BusinessException("O número máximo de jogadores não pode ser menor que o mínimo.");
        }

        if (command.linkInstructionManual() != null && !isValidUrl(command.linkInstructionManual())) {
            throw new BusinessException("O link do manual de instruções é inválido.");
        }

        if (command.linkVideoTutorial() != null && !isValidUrl(command.linkVideoTutorial())) {
            throw new BusinessException("O link do vídeo tutorial é inválido.");
        }

        Game game = new Game();
        game.setBarcode(command.barcode());
        game.setTitle(command.title());
        game.setCategory(GameCategory.valueOf(command.category().toUpperCase()));
        game.setDescription(command.description());
        game.setMinPlayers(command.minPlayers());
        game.setMaxPlayers(command.maxPlayers());
        game.setLinkInstructionManual(command.linkInstructionManual());
        game.setLinkVideoTutorial(command.linkVideoTutorial());

        repository.save(game);

        return "Jogo cadastrado com sucesso!";
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
