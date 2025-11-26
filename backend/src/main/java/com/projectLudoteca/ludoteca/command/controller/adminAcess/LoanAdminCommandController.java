package com.projectLudoteca.ludoteca.command.controller.adminAcess;

import com.projectLudoteca.ludoteca.command.loanGameInEvent.LoanGameInEventCommand;
import com.projectLudoteca.ludoteca.command.loanGameInEvent.LoanGameInEventHandler;
import com.projectLudoteca.ludoteca.command.registerEvent.CreateEventCommand;
import com.projectLudoteca.ludoteca.command.returnedGame.ReturnGameHandler;
import com.projectLudoteca.ludoteca.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/commands/admin/loans")
public class LoanAdminCommandController {

    private final LoanGameInEventHandler loanGameInEventHandler;
    private final ReturnGameHandler returnGameHandler;

    public LoanAdminCommandController(LoanGameInEventHandler loanGameInEventHandler, ReturnGameHandler returnGameHandler) {
        this.loanGameInEventHandler = loanGameInEventHandler;
        this.returnGameHandler = returnGameHandler;
    }

    @PostMapping("/loan-event")
    @Operation(summary = "Registrar um empréstimo em um evento", description = "Cria um empréstimo com dados válidos recebendo o id do evento, o id público do usuário e o id do jogo.")
    public ResponseEntity<ApiResponse<String>> createLoan(@RequestBody @Validated LoanGameInEventCommand command) {

        String message = loanGameInEventHandler.handle(command);

        ApiResponse<String> response = new ApiResponse<>(message);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{id}/return-game")
    @Operation(summary = "Registrar a devolução do empréstimo", description = "Efetua a devolução do jogo emprestado recebendo o id do jogo.")
    public ResponseEntity<ApiResponse<String>> returnLoan(@PathVariable @Validated String id) {

        String message = returnGameHandler.handle(id);

        ApiResponse<String> response = new ApiResponse<>(message);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
