package com.projectLudoteca.ludoteca.query.controller.userAcess;

import com.projectLudoteca.ludoteca.common.response.ApiResponse;
import com.projectLudoteca.ludoteca.query.eventDetails.GetEventDetailsHandler;
import com.projectLudoteca.ludoteca.query.eventDetails.GetEventDetailsView;
import com.projectLudoteca.ludoteca.query.nextEvents.GetNextEventsHandler;
import com.projectLudoteca.ludoteca.query.nextEvents.GetNextEventsView;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/queries/events")
public class EventQueryController {

    private final GetEventDetailsHandler eventDetailsHandler;
    private final GetNextEventsHandler nextEventsHandler;

    public EventQueryController(GetEventDetailsHandler eventDetailsHandler, GetNextEventsHandler nextEventsHandler) {
        this.eventDetailsHandler = eventDetailsHandler;
        this.nextEventsHandler = nextEventsHandler;
    }

    @GetMapping("/{id}/details")
    @Operation(summary = "Lista os dados de um evento", description = "Realiza uma busca de um determinado evento no sistema e retorna seus dados.")
    public ResponseEntity<ApiResponse<GetEventDetailsView>> getEventDetails(@PathVariable String id) {

        GetEventDetailsView view = eventDetailsHandler.handle(id);

        ApiResponse<GetEventDetailsView> response = new ApiResponse<>(view);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/list-next-events")
    @Operation(summary = "Lista os próximos eventos no sistema", description = "Realiza uma busca de todos os eventos agendados que ocorrerão cadastrados no sistema para o usuário")
    public ResponseEntity<ApiResponse<List<GetNextEventsView>>> getNextEvents() {

        List<GetNextEventsView> viewList = nextEventsHandler.handle();

        ApiResponse<List<GetNextEventsView>> response = new ApiResponse<>(viewList);

        return ResponseEntity.ok(response);
    }

}
