package com.projectLudoteca.ludoteca.query.controller.adminAcess;

import com.projectLudoteca.ludoteca.command.registerParticipationEvent.CreateParticipationEventCommand;
import com.projectLudoteca.ludoteca.command.registerParticipationEvent.CreateParticipationEventHandler;
import com.projectLudoteca.ludoteca.command.registerUser.CreateUserCommand;
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
@RequestMapping("/command/admin/participations-event")
public class ParticipationEventAdminCommandController {

    private final CreateParticipationEventHandler registerParticipationEventHandler;

    public ParticipationEventAdminCommandController (CreateParticipationEventHandler registerParticipationEventHandler) {
        this.registerParticipationEventHandler = registerParticipationEventHandler;
    }

    @PostMapping("/register-participation-event")
    @Operation(summary = "Registrar a participação do usuário no evento", description = "Registra a participação do usuário no evento recebendo o id público do usuário e o id do evento")
    public ResponseEntity<ApiResponse<String>> createParticipationEvent(@RequestBody @Validated CreateParticipationEventCommand command) {

        String message = registerParticipationEventHandler.handle(command);

        ApiResponse<String> response = new ApiResponse<>(message);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
}


