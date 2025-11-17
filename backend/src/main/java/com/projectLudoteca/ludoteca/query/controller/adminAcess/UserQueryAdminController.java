package com.projectLudoteca.ludoteca.query.controller.adminAcess;

import com.projectLudoteca.ludoteca.common.response.ApiResponse;
import com.projectLudoteca.ludoteca.query.listAllUsersAdminForAdmin.GetAllUsersForAdminHandler;
import com.projectLudoteca.ludoteca.query.listAllUsersAdminForAdmin.GetAllUsersForAdminView;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/queries/admin/users")
public class UserQueryAdminController {

    private final GetAllUsersForAdminHandler handler;

    public UserQueryAdminController(GetAllUsersForAdminHandler handler) {
        this.handler = handler;
    }

    @GetMapping("/list-all-users")
    @Operation(summary = "Lista de todos os usuários do sistema", description = "Realiza uma busca de todos os usuários do sistema para o administrador")
    public ResponseEntity<ApiResponse<List<GetAllUsersForAdminView>>> getAllUsers() {
        List<GetAllUsersForAdminView> viewList = handler.handleGetAllUsers();

        ApiResponse<List<GetAllUsersForAdminView>> response = new ApiResponse<>(viewList);

        return ResponseEntity.ok(response);
    }

}
