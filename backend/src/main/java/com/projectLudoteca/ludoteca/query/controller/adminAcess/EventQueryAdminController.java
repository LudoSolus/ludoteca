package com.projectLudoteca.ludoteca.query.controller.adminAcess;

import com.projectLudoteca.ludoteca.common.response.ApiResponse;
import com.projectLudoteca.ludoteca.query.listAllEventsForAdmin.GetAllEventsForAdminHandler;
import com.projectLudoteca.ludoteca.query.listAllEventsForAdmin.GetAllEventsForAdminView;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/queries/admin/events")
public class EventQueryAdminController {

    private final GetAllEventsForAdminHandler allEventsForAdminHandler;

    public EventQueryAdminController(GetAllEventsForAdminHandler allEventsForAdminHandler) {
        this.allEventsForAdminHandler = allEventsForAdminHandler;
    }

    @GetMapping("/list-all-events")
    @Operation(summary = "Lista de todos os eventos do sistema", description = "Realiza uma busca de todos os eventos do sistema para o administrador")
    public ResponseEntity<ApiResponse<List<GetAllEventsForAdminView>>> getAllGames() {

        List<GetAllEventsForAdminView> viewList = allEventsForAdminHandler.handle();

        ApiResponse<List<GetAllEventsForAdminView>> response = new ApiResponse<>(viewList);

        return ResponseEntity.ok(response);
    }
}
