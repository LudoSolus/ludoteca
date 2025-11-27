package com.projectLudoteca.ludoteca.query.controller.adminAcess;

import com.projectLudoteca.ludoteca.common.response.ApiResponse;
import com.projectLudoteca.ludoteca.query.detailsGameAdmin.GetGameDetailsAdminHandler;
import com.projectLudoteca.ludoteca.query.detailsGameAdmin.GetGameDetailsAdminView;
import com.projectLudoteca.ludoteca.query.getGameByBarcode.GetGameByBarcodeAdminView;
import com.projectLudoteca.ludoteca.query.getGameByBarcode.GetGameByBarcodeHandler;
import com.projectLudoteca.ludoteca.query.getGameByBarcode.GetGameByBarcodeQuery;
import com.projectLudoteca.ludoteca.query.listAllGamesForAdmin.GetAllGamesForAdminHandler;
import com.projectLudoteca.ludoteca.query.listAllGamesForAdmin.GetAllGamesForAdminView;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/queries/admin/games")
public class GameQueryAdminController {

    private final GetGameDetailsAdminHandler gameDetailsAdminHandler;
    private final GetGameByBarcodeHandler gameByBarcodeHandler;
    private final GetAllGamesForAdminHandler allGamesForAdminHandler;

    public GameQueryAdminController(GetGameDetailsAdminHandler gameDetailsAdminHandler, GetGameByBarcodeHandler gameByBarcodeHandler, GetAllGamesForAdminHandler allGamesForAdminHandler) {
        this.gameDetailsAdminHandler = gameDetailsAdminHandler;
        this.gameByBarcodeHandler = gameByBarcodeHandler;
        this.allGamesForAdminHandler = allGamesForAdminHandler;
    }

    @GetMapping("/{id}/details")
    @Operation(summary = "Lista os dados de um jogo", description = "Realiza uma busca de um determinado jogo no sistema e retorna seus dados para o administrador.")
    public ResponseEntity<ApiResponse<GetGameDetailsAdminView>> getDetails(@PathVariable String id) {

        GetGameDetailsAdminView view = gameDetailsAdminHandler.handle(id);

        ApiResponse<GetGameDetailsAdminView> response = new ApiResponse<>(view);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{barcode}/get-game")
    @Operation(summary = "Busca um jogo", description = "Realiza uma busca de um determinado jogo no sistema pelo seu código de barras e retorna seu id e nome para o administrador.")
    public ResponseEntity<ApiResponse<GetGameByBarcodeAdminView>> getGameDetailsAdminHandler(@PathVariable Integer barcode) {

        GetGameByBarcodeAdminView view = gameByBarcodeHandler.handle(new GetGameByBarcodeQuery(barcode));

        ApiResponse<GetGameByBarcodeAdminView> response = new ApiResponse<>(view);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/list-all-games")
    @Operation(summary = "Lista de todos os jogos do sistema", description = "Realiza uma busca de todos os jogos do sistema para o administrador")
    public ResponseEntity<ApiResponse<List<GetAllGamesForAdminView>>> getAllGames() {
        List<GetAllGamesForAdminView> viewList = allGamesForAdminHandler.handle();

        ApiResponse<List<GetAllGamesForAdminView>> response = new ApiResponse<>(viewList);

        return ResponseEntity.ok(response);
    }

}
