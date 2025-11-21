package com.projectLudoteca.ludoteca.command.controller;

import com.projectLudoteca.ludoteca.command.registerEvent.CreateEventCommand;
import com.projectLudoteca.ludoteca.command.registerEvent.CreateEventHandler;
import com.projectLudoteca.ludoteca.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/commands/admin/events")
@Validated
public class EventCommandController {

    private final CreateEventHandler createEventHandler;

    public EventCommandController(CreateEventHandler createEventHandler) {
        this.createEventHandler = createEventHandler;
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar novo jogo", description = "Cria um jogo com dados válidos")
    public ResponseEntity<ApiResponse<String>> createGame(@RequestBody @Validated CreateEventCommand command) {

        String message = createEventHandler.handle(command);

        ApiResponse<String> response = new ApiResponse<>(message);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
