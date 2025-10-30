package com.projectLudoteca.ludoteca.query.controller;

import com.projectLudoteca.ludoteca.common.response.ApiResponse;
import com.projectLudoteca.ludoteca.query.handler.GetAllUsersHandler;
import com.projectLudoteca.ludoteca.query.model.UserView;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/queries/users")
public class UserQueryController {

    private final GetAllUsersHandler handler;

    public UserQueryController(GetAllUsersHandler handler) {
        this.handler = handler;
    }

    @GetMapping("/list-all-users")
    @Operation(summary = "Lista de todos os usuários do sistema", description = "Realiza uma busca de todos os usuários do sistema")
    public ResponseEntity<ApiResponse<UserView>> getAllUsers() {
        List<UserView> users = handler.handleGetAllUsers();

        ApiResponse<UserView> response = new ApiResponse<>();
        response.setResultList(users);

        return ResponseEntity.ok(response);
    }

}
