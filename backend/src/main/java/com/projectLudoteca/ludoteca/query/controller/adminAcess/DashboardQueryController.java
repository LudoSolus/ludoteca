package com.projectLudoteca.ludoteca.query.controller.adminAcess;

import com.projectLudoteca.ludoteca.common.response.ApiResponse;
import com.projectLudoteca.ludoteca.query.dashboard.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/queries/admin/dashboard")
public class DashboardQueryController {

    private final GetParticipantsByEventHandler getParticipantsByEventHandler;
    private final GetMostPlayedGamesHandler getMostPlayedGamesHandler;
    private final GetRegisteredUsersCountHandler getRegisteredUsersCountHandler;

    public DashboardQueryController(
            GetParticipantsByEventHandler getParticipantsByEventHandler,
            GetMostPlayedGamesHandler getMostPlayedGamesHandler,
            GetRegisteredUsersCountHandler getRegisteredUsersCountHandler) {
        this.getParticipantsByEventHandler = getParticipantsByEventHandler;
        this.getMostPlayedGamesHandler = getMostPlayedGamesHandler;
        this.getRegisteredUsersCountHandler = getRegisteredUsersCountHandler;
    }

    @GetMapping("/participants-by-event")
    @Operation(summary = "Obter participantes por evento", description = "Retorna um gráfico com a quantidade de participantes em cada evento")
    public ResponseEntity<ApiResponse<ParticipantsByEventResponse>> getParticipantsByEvent() {
        ParticipantsByEventResponse response = getParticipantsByEventHandler.handle();
        return ResponseEntity.ok(new ApiResponse<>(response));
    }

    @GetMapping("/most-played-games")
    @Operation(summary = "Obter jogos mais jogados", description = "Retorna um gráfico com os jogos mais emprestados/jogados")
    public ResponseEntity<ApiResponse<MostPlayedGamesResponse>> getMostPlayedGames() {
        MostPlayedGamesResponse response = getMostPlayedGamesHandler.handle();
        return ResponseEntity.ok(new ApiResponse<>(response));
    }

    @GetMapping("/registered-users-count")
    @Operation(summary = "Obter quantidade de usuários", description = "Retorna a quantidade total de usuários cadastrados no sistema")
    public ResponseEntity<ApiResponse<RegisteredUsersCountResponse>> getRegisteredUsersCount() {
        RegisteredUsersCountResponse response = getRegisteredUsersCountHandler.handle();
        return ResponseEntity.ok(new ApiResponse<>(response));
    }
}
