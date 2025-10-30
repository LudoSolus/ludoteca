package com.projectLudoteca.ludoteca.command.controller;

import com.projectLudoteca.ludoteca.command.loginUser.LoginUserCommand;
import com.projectLudoteca.ludoteca.command.loginUser.LoginUserHandler;
import com.projectLudoteca.ludoteca.command.registerUser.CreateUserCommand;
import com.projectLudoteca.ludoteca.command.registerUser.CreateUserHandler;
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

    private final CreateUserHandler registerHandler;
    private final LoginUserHandler loginHandler;

    public UserCommandController(CreateUserHandler registerHandler, LoginUserHandler loginHandler) {
        this.registerHandler = registerHandler;
        this.loginHandler = loginHandler;
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar novo usuário", description = "Cria um usuário com dados válidos")
    public ResponseEntity<ApiResponse<String>> createUser(@RequestBody @Validated CreateUserCommand command) {

        String token = registerHandler.handle(command);

        ApiResponse<String> response = new ApiResponse<>(token);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PostMapping("/login")
    @Operation(summary = "Login do usuário", description = "Realiza login retornando um token JWT")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody @Validated LoginUserCommand command) {
        String token = loginHandler.login(command);

        ApiResponse<String> response = new ApiResponse<>(token);

        return ResponseEntity.ok(response);
    }
}
