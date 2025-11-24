package com.projectLudoteca.ludoteca.command.controller.adminAcess;

import com.projectLudoteca.ludoteca.command.loanGameInEvent.LoanGameInEventCommand;
import com.projectLudoteca.ludoteca.command.loanGameInEvent.LoanGameInEventHandler;
import com.projectLudoteca.ludoteca.command.registerEvent.CreateEventCommand;
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
@RequestMapping("/commands/admin/loans")
public class LoanAdminCommandController {

    private final LoanGameInEventHandler loanGameInEventHandler;

    public LoanAdminCommandController(LoanGameInEventHandler loanGameInEventHandler) {
        this.loanGameInEventHandler = loanGameInEventHandler;
    }

    @PostMapping("/loan-event")
    @Operation(summary = "Registrar um empréstimo em eu evento", description = "Cria um empréstimo com dados válidos recebendo o id do evento, o id público do usuário e o id do jogo.")
    public ResponseEntity<ApiResponse<String>> createGame(@RequestBody @Validated LoanGameInEventCommand command) {

        String message = loanGameInEventHandler.handle(command);

        ApiResponse<String> response = new ApiResponse<>(message);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
