package com.projectLudoteca.ludoteca.command.controller.adminAcess;

import com.projectLudoteca.ludoteca.command.registerGame.CreateGameCommand;
import com.projectLudoteca.ludoteca.command.registerGame.CreateGameHandler;
import com.projectLudoteca.ludoteca.command.updateGame.UpdateGameCommand;
import com.projectLudoteca.ludoteca.command.updateGame.UpdateGameHandler;
import com.projectLudoteca.ludoteca.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/commands/admin/games")
public class GameAdminCommandController {

    private final CreateGameHandler createGameHandler;
    private final UpdateGameHandler updateGameHandler;

    @Autowired
    public GameAdminCommandController(CreateGameHandler createGameHandler, UpdateGameHandler updateGameHandler) {
        this.createGameHandler = createGameHandler;
        this.updateGameHandler = updateGameHandler;
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar novo jogo", description = "Cria um jogo com dados válidos")
    public ResponseEntity<ApiResponse<String>> createGame(@RequestBody @Validated CreateGameCommand command) {

        String message = createGameHandler.handle(command);

        ApiResponse<String> response = new ApiResponse<>(message);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}/update")
    @Operation(summary = "Atualiza dados do jogo", description = "Realiza a atualização dos dados do jogo.")
    public ResponseEntity<ApiResponse<String>> update(@PathVariable String id, @RequestBody UpdateGameCommand command) {

        String message = updateGameHandler.handle(id, command);

        ApiResponse<String> response = new ApiResponse<>(message);

        return ResponseEntity.ok(response);
    }

}
