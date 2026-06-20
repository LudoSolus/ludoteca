package com.projectLudoteca.ludoteca.query.controller.userAcess;

import com.projectLudoteca.ludoteca.common.entity.User;
import com.projectLudoteca.ludoteca.common.response.ApiResponse;
import com.projectLudoteca.ludoteca.query.detailsUser.GetUserDetailsHandler;
import com.projectLudoteca.ludoteca.query.detailsUser.GetUserDetailsView;
import com.projectLudoteca.ludoteca.query.detailsUserAdmin.GetUserDetailsAdminView;
import com.projectLudoteca.ludoteca.query.listAllUsersAdminForAdmin.GetAllUsersForAdminHandler;
import com.projectLudoteca.ludoteca.query.listAllUsersAdminForAdmin.GetAllUsersForAdminView;
import com.projectLudoteca.ludoteca.query.userHistory.ListRecentGamesPlayedByUserHandler;
import com.projectLudoteca.ludoteca.query.userHistory.RecentGamePlayedView;
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
    private final ListRecentGamesPlayedByUserHandler recentGamesHandler;

    public UserQueryController(GetUserDetailsHandler userDetailsHandler,
            ListRecentGamesPlayedByUserHandler recentGamesHandler) {
        this.userDetailsHandler = userDetailsHandler;
        this.recentGamesHandler = recentGamesHandler;
    }

    @GetMapping("/profile-user")
    @Operation(summary = "Detalhes do usuário", description = "Apresenta os dados pessoais do usuário.")
    public ResponseEntity<ApiResponse<GetUserDetailsView>> getUserDetails(@AuthenticationPrincipal User user) {
        GetUserDetailsView viewList = userDetailsHandler.handle(user);

        ApiResponse<GetUserDetailsView> response = new ApiResponse<>(viewList);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/history/recent-games")
    @Operation(summary = "Últimos 20 jogos jogados", description = "Retorna os últimos 20 jogos emprestados pelo usuário autenticado, ordenados do mais recente para o mais antigo.", tags = {
            "User - Histórico" })
    public ResponseEntity<ApiResponse<List<RecentGamePlayedView>>> getRecentGamesPlayedByUser(
            @AuthenticationPrincipal User user) {
        List<RecentGamePlayedView> recentGames = recentGamesHandler.handle(user);

        ApiResponse<List<RecentGamePlayedView>> response = new ApiResponse<>(recentGames);

        return ResponseEntity.ok(response);
    }
}
