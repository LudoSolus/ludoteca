package com.projectLudoteca.ludoteca.query.listAllGamesForAdmin;

import com.projectLudoteca.ludoteca.common.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllGamesForAdminHandler {

    private final GameRepository gameRepository;

    public GetAllGamesForAdminHandler(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<GetAllGamesForAdminView> handle() {

        return gameRepository.findAll()
                .stream()
                .map(g -> new GetAllGamesForAdminView(g.getId(), g.getBarcode(), g.getTitle(), g.getCategory().name(), g.getMinPlayers(), g.getMaxPlayers()))
                .toList();
    }
}
