package com.projectLudoteca.ludoteca.command.controller;

import com.projectLudoteca.ludoteca.command.model.CreateUserCommand;
import com.projectLudoteca.ludoteca.command.model.LoginUserCommand;
import com.projectLudoteca.ludoteca.command.service.UserCommandService;
import com.projectLudoteca.ludoteca.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/commands/users")
@Validated
public class UserCommandController {

    private final UserCommandService service;

    public UserCommandController(UserCommandService service) {
        this.service = service;
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar novo usuário", description = "Cria um usuário com dados válidos")
    public ResponseEntity<ApiResponse<String>> createUser(@RequestBody @Validated CreateUserCommand command) {
        String result = service.createUser(command);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.created("Usuário registrado com sucesso", result));
    }

    @PostMapping("/login")
    @Operation(summary = "Login do usuário", description = "Realiza login retornando um token JWT")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody @Validated LoginUserCommand command) {
        String token = service.login(command);
        return ResponseEntity
                .ok(ApiResponse.success("Login realizado com sucesso", token));
    }
}
