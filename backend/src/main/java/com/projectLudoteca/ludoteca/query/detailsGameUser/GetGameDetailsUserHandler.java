package com.projectLudoteca.ludoteca.query.detailsGameUser;

import com.projectLudoteca.ludoteca.common.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class GetGameDetailsUserHandler {

    private final GameRepository gameRepository;

    @Autowired
    public GetGameDetailsUserHandler(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public GetGameDetailsUserView handle(GetGameDetailsUserQuery query) {

        if (query.id() == null) {
            throw new IllegalArgumentException("O id não pode ser nulo.");
        }

        return gameRepository.findById(query.id())
                .map(game -> new GetGameDetailsUserView(
                        game.getId(),
                        game.getBarcode(),
                        game.getTitle(),
                        game.getDescription(),
                        game.getCategory().name(),
                        game.getMinPlayers(),
                        game.getMaxPlayers(),
                        game.getLinkInstructionManual(),
                        game.getLinkVideoTutorial()
                ))
                .orElseThrow(() -> new NoSuchElementException("Jogo não encontrado."));
    }

}
