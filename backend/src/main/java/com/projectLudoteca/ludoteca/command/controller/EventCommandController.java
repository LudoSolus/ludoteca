package com.projectLudoteca.ludoteca.command.controller;

import com.projectLudoteca.ludoteca.command.registerEvent.CreateEventCommand;
import com.projectLudoteca.ludoteca.command.registerEvent.CreateEventHandler;
import com.projectLudoteca.ludoteca.command.updateEvent.UpdateEventCommand;
import com.projectLudoteca.ludoteca.command.updateEvent.UpdateEventHandler;
import com.projectLudoteca.ludoteca.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/commands/admin/events")
@Validated
public class EventCommandController {

    private final CreateEventHandler createEventHandler;
    private final UpdateEventHandler updateEventHandler;

    public EventCommandController(CreateEventHandler createEventHandler, UpdateEventHandler updateEventHandler) {
        this.createEventHandler = createEventHandler;
        this.updateEventHandler = updateEventHandler;
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar novo jogo", description = "Cria um jogo com dados válidos")
    public ResponseEntity<ApiResponse<String>> createGame(@RequestBody @Validated CreateEventCommand command) {

        String message = createEventHandler.handle(command);

        ApiResponse<String> response = new ApiResponse<>(message);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("{id}/update")
    @Operation(summary = "Atualiza dados do evento", description = "Realiza a atualização dos dados do evento")
    public ResponseEntity<ApiResponse<String>> update(@RequestBody @Validated UpdateEventCommand command, @PathVariable UUID id) {

        String message = updateEventHandler.handle(id, command);

        ApiResponse<String> response = new ApiResponse<>(message);

        return ResponseEntity.ok(response);
    }

}
