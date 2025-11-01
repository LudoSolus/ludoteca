package com.projectLudoteca.ludoteca.query.controller.userAcess;

import com.projectLudoteca.ludoteca.common.response.ApiResponse;
import com.projectLudoteca.ludoteca.query.gameDetailsUser.GetGameDetailsUserHandler;
import com.projectLudoteca.ludoteca.query.gameDetailsUser.GetGameDetailsUserQuery;
import com.projectLudoteca.ludoteca.query.gameDetailsUser.GetGameDetailsUserView;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/queries/games")
public class GameQueryController {

    private final GetGameDetailsUserHandler gameDetailsUserHandler;

    public GameQueryController(GetGameDetailsUserHandler gameDetailsUserHandler) {
        this.gameDetailsUserHandler = gameDetailsUserHandler;
    }

    @GetMapping("/{barcode}/details")
    @Operation(summary = "Lista os dados de um jogo", description = "Realiza uma busca de um determinado jogo no sistema e retorna seus dados para o usuário.")
    public ResponseEntity<ApiResponse<GetGameDetailsUserView>> getDetails(@PathVariable Integer barcode) {

        GetGameDetailsUserView view = gameDetailsUserHandler.handle(new GetGameDetailsUserQuery(barcode));

        ApiResponse<GetGameDetailsUserView> response = new ApiResponse<>(view);

        return ResponseEntity.ok(response);
    }

}
