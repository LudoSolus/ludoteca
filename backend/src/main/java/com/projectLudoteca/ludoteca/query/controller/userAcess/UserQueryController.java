package com.projectLudoteca.ludoteca.query.controller.userAcess;

import com.projectLudoteca.ludoteca.common.entity.User;
import com.projectLudoteca.ludoteca.common.response.ApiResponse;
import com.projectLudoteca.ludoteca.query.detailsUser.GetUserDetailsHandler;
import com.projectLudoteca.ludoteca.query.detailsUser.GetUserDetailsView;
import com.projectLudoteca.ludoteca.query.detailsUserAdmin.GetUserDetailsAdminView;
import com.projectLudoteca.ludoteca.query.listAllUsersAdminForAdmin.GetAllUsersForAdminHandler;
import com.projectLudoteca.ludoteca.query.listAllUsersAdminForAdmin.GetAllUsersForAdminView;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/queries/users")
public class UserQueryController {

    private final GetUserDetailsHandler userDetailsHandler;

    public UserQueryController(GetUserDetailsHandler userDetailsHandler) {
        this.userDetailsHandler = userDetailsHandler;
    }

    @GetMapping("/profile-user")
    @Operation(summary = "Detalhes do usuário", description = "Apresenta os dados pessoais do usuário.")
    public ResponseEntity<ApiResponse<GetUserDetailsView>> getUserDetails(@AuthenticationPrincipal User user) {
        GetUserDetailsView viewList = userDetailsHandler.handle(user);

        ApiResponse<GetUserDetailsView> response = new ApiResponse<>(viewList);

        return ResponseEntity.ok(response);
    }
}
