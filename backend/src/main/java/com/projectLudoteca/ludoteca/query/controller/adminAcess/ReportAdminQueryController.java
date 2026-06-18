package com.projectLudoteca.ludoteca.query.controller.adminAcess;

import com.projectLudoteca.ludoteca.common.response.ApiResponse;
import com.projectLudoteca.ludoteca.query.reports.defaulters.GetDefaultersReportHandler;
import com.projectLudoteca.ludoteca.query.reports.defaulters.DefaulterUserView;
import com.projectLudoteca.ludoteca.query.reports.seasonality.GetLoansSeasonalityHandler;
import com.projectLudoteca.ludoteca.query.reports.seasonality.LoansSeasonalityView;
import com.projectLudoteca.ludoteca.query.reports.topGames.GetTopGamesReportHandler;
import com.projectLudoteca.ludoteca.query.reports.topGames.TopGameView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/queries/admin/reports")
@Tag(name = "Admin - Relatórios", description = "Consultas e estatísticas para gestão da Ludoteca")
public class ReportAdminQueryController {

    private final GetTopGamesReportHandler topGamesHandler;
    private final GetDefaultersReportHandler defaultersHandler;
    private final GetLoansSeasonalityHandler seasonalityHandler;

    public ReportAdminQueryController(
            GetTopGamesReportHandler topGamesHandler,
            GetDefaultersReportHandler defaultersHandler,
            GetLoansSeasonalityHandler seasonalityHandler) {
        this.topGamesHandler = topGamesHandler;
        this.defaultersHandler = defaultersHandler;
        this.seasonalityHandler = seasonalityHandler;
    }

    @GetMapping("/top-games")
    @Operation(summary = "Ranking de Jogos Mais Populares", description = "Retorna os jogos com maior quantidade histórica de empréstimos, ordenados do mais jogado para o menos jogado.")
    public ResponseEntity<ApiResponse<List<TopGameView>>> getTopGames() {
        
        List<TopGameView> reportData = topGamesHandler.handle();
        
        ApiResponse<List<TopGameView>> response = new ApiResponse<>(reportData);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/defaulters")
    @Operation(summary = "Relatório de Atrasos e Inadimplência", description = "Lista os usuários que não devolveram jogos no prazo, incluindo o cálculo de dias de atraso.")
    public ResponseEntity<ApiResponse<List<DefaulterUserView>>> getDefaulters() {
        
        List<DefaulterUserView> reportData = defaultersHandler.handle();
        
        return ResponseEntity.ok(new ApiResponse<>(reportData));
    }

    @GetMapping("/seasonality")
    @Operation(summary = "Sazonalidade e Picos de Empréstimo", description = "Retorna o volume total de empréstimos agrupados por mês e ano, ordenados do mais recente para o mais antigo.")
    public ResponseEntity<ApiResponse<List<LoansSeasonalityView>>> getSeasonality() {
        
        List<LoansSeasonalityView> reportData = seasonalityHandler.handle();
        
        return ResponseEntity.ok(new ApiResponse<>(reportData));
    }
}