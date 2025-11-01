package com.projectLudoteca.ludoteca.query.controller.adminAcess;

import com.projectLudoteca.ludoteca.common.response.ApiResponse;
import com.projectLudoteca.ludoteca.query.gameDetailsAdmin.GetGameDetailsAdminHandler;
import com.projectLudoteca.ludoteca.query.gameDetailsAdmin.GetGameDetailsAdminQuery;
import com.projectLudoteca.ludoteca.query.gameDetailsAdmin.GetGameDetailsAdminView;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/queries/admin/games")
public class GameQueryAdminController {

    private final GetGameDetailsAdminHandler gameDetailsAdminHandler;

    public GameQueryAdminController(GetGameDetailsAdminHandler gameDetailsAdminHandler) {
        this.gameDetailsAdminHandler = gameDetailsAdminHandler;
    }

    @GetMapping("/{barcode}/details")
    @Operation(summary = "Lista os dados de um jogo", description = "Realiza uma busca de um determinado jogo no sistema e retorna seus dados para o administrador.")
    public ResponseEntity<ApiResponse<GetGameDetailsAdminView>> getDetails(@PathVariable Integer barcode) {

        GetGameDetailsAdminView view = gameDetailsAdminHandler.handle(new GetGameDetailsAdminQuery(barcode));

        ApiResponse<GetGameDetailsAdminView> response = new ApiResponse<>(view);

        return ResponseEntity.ok(response);
    }

}
