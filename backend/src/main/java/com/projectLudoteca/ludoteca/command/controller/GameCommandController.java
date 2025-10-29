package com.projectLudoteca.ludoteca.command.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projectLudoteca.ludoteca.command.service.GameCommandService;

@RestController
@RequestMapping("/commands/games")
public class GameCommandController {

    private final GameCommandService service;

    public GameCommandController(GameCommandService service) {
        this.service = service;
    }

}
