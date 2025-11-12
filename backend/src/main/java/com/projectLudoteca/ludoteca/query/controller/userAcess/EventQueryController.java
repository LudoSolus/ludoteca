package com.projectLudoteca.ludoteca.query.controller.userAcess;

import com.projectLudoteca.ludoteca.common.response.ApiResponse;
import com.projectLudoteca.ludoteca.query.eventDetails.GetEventDetailsHandler;
import com.projectLudoteca.ludoteca.query.eventDetails.GetEventDetailsQuery;
import com.projectLudoteca.ludoteca.query.eventDetails.GetEventDetailsView;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/queries/events")
public class EventQueryController {

    private final GetEventDetailsHandler getEventDetailsHandler;

    public EventQueryController(GetEventDetailsHandler getEventDetailsHandler) {
        this.getEventDetailsHandler = getEventDetailsHandler;
    }

    @GetMapping("/{id}/details")
    @Operation(summary = "Lista os dados de um evento", description = "Realiza uma busca de um determinado evento no sistema e retorna seus dados.")
    public ResponseEntity<ApiResponse<GetEventDetailsView>> getEventDetails(@PathVariable UUID id) {

        GetEventDetailsView view = getEventDetailsHandler.handle(new GetEventDetailsQuery(id));

        ApiResponse<GetEventDetailsView> response = new ApiResponse<>(view);

        return ResponseEntity.ok(response);
    }

}
