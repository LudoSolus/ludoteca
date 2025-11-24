package com.projectLudoteca.ludoteca.command.controller.adminAcess;

import com.projectLudoteca.ludoteca.command.registerGame.CreateGameCommand;
import com.projectLudoteca.ludoteca.command.registerGame.CreateGameHandler;
import com.projectLudoteca.ludoteca.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/commands/admin/games")
public class GameAdminCommandController {

    private final CreateGameHandler createGameHandler;

    @Autowired
    public GameAdminCommandController(CreateGameHandler createGameHandler) {
        this.createGameHandler = createGameHandler;
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar novo jogo", description = "Cria um jogo com dados válidos")
    public ResponseEntity<ApiResponse<String>> createGame(@RequestBody @Validated CreateGameCommand command) {

        String message = createGameHandler.handle(command);

        ApiResponse<String> response = new ApiResponse<>(message);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
