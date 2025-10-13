package com.projectLudoteca.ludoteca.command.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectLudoteca.ludoteca.common.repository.GameRepository;

@Service
public class GameCommandService {

    private final GameRepository gameRepository;

    @Autowired
    public GameCommandService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

}
