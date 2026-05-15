package com.projectLudoteca.ludoteca.command.controller.adminAcess;

import com.projectLudoteca.ludoteca.command.changeRoleUser.ChangeRoleUserCommand;
import com.projectLudoteca.ludoteca.command.changeRoleUser.ChangeRoleUserHandler;
import com.projectLudoteca.ludoteca.command.deleteUser.DeleteUserCommand;
import com.projectLudoteca.ludoteca.command.deleteUser.DeleteUserHandler;
import com.projectLudoteca.ludoteca.command.registerUserAdmin.CreateUserAdminCommand;
import com.projectLudoteca.ludoteca.command.registerUserAdmin.CreateUserAdminHandler;
import com.projectLudoteca.ludoteca.common.entity.User;
import com.projectLudoteca.ludoteca.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/commands/admin/users")
@Validated
public class UserAdminCommandController {

    private final CreateUserAdminHandler registerUserAdminHandler;
    private final ChangeRoleUserHandler changeRoleUserHandler;
    private final DeleteUserHandler deleteUserHandler;

    public UserAdminCommandController(CreateUserAdminHandler registerUserAdminHandler, ChangeRoleUserHandler changeRoleUserHandler, DeleteUserHandler deleteUserHandler) {
        this.registerUserAdminHandler = registerUserAdminHandler;
        this.changeRoleUserHandler = changeRoleUserHandler;
        this.deleteUserHandler = deleteUserHandler;
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar novo usuário por Admin", description = "Um admin cria um usuário com dados válidos")
    public ResponseEntity<ApiResponse<String>> createUser(@RequestBody @Validated CreateUserAdminCommand command) {

        String token = registerUserAdminHandler.handle(command);

        ApiResponse<String> response = new ApiResponse<>(token);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PostMapping("/{id}/change-role")
    @Operation(summary = "Altera o tipo do usuário", description = "Um admin altera qual tipo um usuário pode ser.")
    public ResponseEntity<ApiResponse<String>> changeRole(@PathVariable String id, @RequestBody ChangeRoleUserCommand command) {

        String token = changeRoleUserHandler.handle(id, command);

        ApiResponse<String> response = new ApiResponse<>(token);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover usuário (exclusão lógica)", description = "Um admin realiza a remoção lógica de um usuário, preservando seu histórico de empréstimos e participações em eventos.")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable String id, @AuthenticationPrincipal User authenticatedAdmin) {

        String message = deleteUserHandler.handle(id, authenticatedAdmin);

        ApiResponse<String> response = new ApiResponse<>(message);

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

}
