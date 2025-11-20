package com.projectLudoteca.ludoteca.query.controller.adminAcess;

import com.projectLudoteca.ludoteca.common.response.ApiResponse;
import com.projectLudoteca.ludoteca.query.detailsUserAdmin.GetUserDetailsAdminHandler;
import com.projectLudoteca.ludoteca.query.detailsUserAdmin.GetUserDetailsAdminQuery;
import com.projectLudoteca.ludoteca.query.detailsUserAdmin.GetUserDetailsAdminView;
import com.projectLudoteca.ludoteca.query.listAllUsersAdminForAdmin.GetAllUsersForAdminHandler;
import com.projectLudoteca.ludoteca.query.listAllUsersAdminForAdmin.GetAllUsersForAdminView;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/queries/admin/users")
public class UserQueryAdminController {

    private final GetAllUsersForAdminHandler allUsersForAdminHandler;
    private final GetUserDetailsAdminHandler userDetailsAdminHandler;

    public UserQueryAdminController(GetAllUsersForAdminHandler allUsersForAdminHandler, GetUserDetailsAdminHandler userDetailsAdminHandler) {
        this.allUsersForAdminHandler = allUsersForAdminHandler;
        this.userDetailsAdminHandler = userDetailsAdminHandler;
    }

    @GetMapping("/list-all-users")
    @Operation(summary = "Lista de todos os usuários do sistema", description = "Realiza uma busca de todos os usuários do sistema para o administrador")
    public ResponseEntity<ApiResponse<List<GetAllUsersForAdminView>>> getAllUsers() {
        List<GetAllUsersForAdminView> viewList = allUsersForAdminHandler.handle();

        ApiResponse<List<GetAllUsersForAdminView>> response = new ApiResponse<>(viewList);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/get-user")
    @Operation(summary = "Busca um usuário", description = "Realiza uma busca de um determinado usuárrio no sistema e retorna seus dados para o administrador.")
    public ResponseEntity<ApiResponse<GetUserDetailsAdminView>> getUserDetails(@PathVariable String id) {
        GetUserDetailsAdminView viewList = userDetailsAdminHandler.handle(new GetUserDetailsAdminQuery(UUID.fromString(id)));

        ApiResponse<GetUserDetailsAdminView> response = new ApiResponse<>(viewList);

        return ResponseEntity.ok(response);
    }

}
