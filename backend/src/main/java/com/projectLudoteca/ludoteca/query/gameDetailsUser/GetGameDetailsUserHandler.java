package com.projectLudoteca.ludoteca.query.gameDetailsUser;

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
        return gameRepository.findByBarcode(query.barcode())
                .map(game -> new GetGameDetailsUserView(
                        game.getTitle(),
                        game.getDescription(),
                        game.getCategory(),
                        game.getMinPlayers(),
                        game.getMaxPlayers(),
                        game.getLinkInstructionManual(),
                        game.getLinkVideoTutorial()
                ))
                .orElseThrow(() -> new NoSuchElementException("Jogo não encontrado."));
    }

}
