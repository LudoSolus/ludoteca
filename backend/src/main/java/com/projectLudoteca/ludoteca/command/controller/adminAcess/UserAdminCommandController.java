package com.projectLudoteca.ludoteca.command.controller.adminAcess;

import com.projectLudoteca.ludoteca.command.registerUser.CreateUserCommand;
import com.projectLudoteca.ludoteca.command.registerUserAdmin.CreateUserAdminCommand;
import com.projectLudoteca.ludoteca.command.registerUserAdmin.CreateUserAdminHandler;
import com.projectLudoteca.ludoteca.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/commands/admin/users")
@Validated
public class UserAdminCommandController {

    private final CreateUserAdminHandler registerUserAdminHandler;

    public UserAdminCommandController(CreateUserAdminHandler registerUserAdminHandler) {
        this.registerUserAdminHandler = registerUserAdminHandler;
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar novo usuário por Admin", description = "Um admin cria um usuário com dados válidos")
    public ResponseEntity<ApiResponse<String>> createUser(@RequestBody @Validated CreateUserAdminCommand command) {

        String token = registerUserAdminHandler.handle(command);

        ApiResponse<String> response = new ApiResponse<>(token);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

}
