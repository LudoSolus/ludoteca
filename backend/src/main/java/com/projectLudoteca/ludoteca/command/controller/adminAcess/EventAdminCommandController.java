package com.projectLudoteca.ludoteca.command.controller.adminAcess;

import com.projectLudoteca.ludoteca.command.endEvent.EndEventHandler;
import com.projectLudoteca.ludoteca.command.registerEvent.CreateEventCommand;
import com.projectLudoteca.ludoteca.command.registerEvent.CreateEventHandler;
import com.projectLudoteca.ludoteca.command.startEvent.StartEventHandler;
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
public class EventAdminCommandController {

    private final CreateEventHandler createEventHandler;
    private final UpdateEventHandler updateEventHandler;
    private final StartEventHandler startEventHandler;
    private final EndEventHandler endEventHandler;

    public EventAdminCommandController(CreateEventHandler createEventHandler, UpdateEventHandler updateEventHandler, StartEventHandler startEventHandler, EndEventHandler endEventHandler) {
        this.createEventHandler = createEventHandler;
        this.updateEventHandler = updateEventHandler;
        this.startEventHandler = startEventHandler;
        this.endEventHandler = endEventHandler;
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar novo evento", description = "Cria um evento com dados válidos")
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

    @PutMapping("{id}/start")
    @Operation(summary = "Inicializa um evento.", description = "Inicia um evento a partir do id do evento.")
    public ResponseEntity<ApiResponse<String>> startEvent(@PathVariable String id) {

        String message = startEventHandler.handle(id);

        ApiResponse<String> response = new ApiResponse<>(message);

        return ResponseEntity.ok(response);
    }

    @PutMapping("{id}/end")
    @Operation(summary = "Finaliza um evento.", description = "Finaliza um evento a partir do id do evento.")
    public ResponseEntity<ApiResponse<String>> endEvent(@PathVariable String id) {

        String message = endEventHandler.handle(id);

        ApiResponse<String> response = new ApiResponse<>(message);

        return ResponseEntity.ok(response);
    }

}
