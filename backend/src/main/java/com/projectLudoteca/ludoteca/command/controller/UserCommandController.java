package com.projectLudoteca.ludoteca.command.controller;

import com.projectLudoteca.ludoteca.command.loginUser.LoginUserCommand;
import com.projectLudoteca.ludoteca.command.loginUser.LoginUserHandler;
import com.projectLudoteca.ludoteca.command.registerUser.CreateUserCommand;
import com.projectLudoteca.ludoteca.command.registerUser.CreateUserHandler;
import com.projectLudoteca.ludoteca.command.updateUser.UpdateUserCommand;
import com.projectLudoteca.ludoteca.command.updateUser.UpdateUserHandler;
import com.projectLudoteca.ludoteca.common.entity.User;
import com.projectLudoteca.ludoteca.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/commands/users")
@Validated
public class UserCommandController {

    private final CreateUserHandler registerHandler;
    private final LoginUserHandler loginHandler;
    private final UpdateUserHandler updateHandler;

    public UserCommandController(CreateUserHandler registerHandler, LoginUserHandler loginHandler, UpdateUserHandler updateHandler) {
        this.registerHandler = registerHandler;
        this.loginHandler = loginHandler;
        this.updateHandler = updateHandler;
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
        String token = loginHandler.handle(command);

        ApiResponse<String> response = new ApiResponse<>(token);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    @Operation(summary = "Atualiza dados do usuário", description = "Realiza a atualização dos dados do usuário")
    public ResponseEntity<ApiResponse<String>> update(@RequestBody @Validated UpdateUserCommand command, @AuthenticationPrincipal User user) {

        String message = updateHandler.handle(user, command);

        ApiResponse<String> response = new ApiResponse<>(message);

        return ResponseEntity.ok(response);
    }
}
