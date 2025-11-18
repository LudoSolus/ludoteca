package com.projectLudoteca.ludoteca.query.eventDetails;

import com.projectLudoteca.ludoteca.common.entity.Event;
import com.projectLudoteca.ludoteca.common.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class GetEventDetailsHandler {

    private EventRepository repository;

    public GetEventDetailsHandler(EventRepository repository) {
        this.repository = repository;
    }

    public GetEventDetailsView handle(GetEventDetailsQuery query) {
        Event event = repository.findById(query.id())
                .orElseThrow(() -> new NoSuchElementException("Evento não encontrado."));

        List<GetEventDetailsView.ListGamesView> games = event.getGamesEvent().stream()
                .filter(ge -> !ge.getRemoved() && ge.getGame() != null)
                .map(ge -> new GetEventDetailsView.ListGamesView(
                        ge.getGame().getTitle(),
                        ge.getGame().getIsAvailable(),
                        ge.getGame().getCategory(),
                        ge.getGame().getMinPlayers(),
                        ge.getGame().getMaxPlayers()
                ))
                .collect(Collectors.toList());

        return new GetEventDetailsView(
                event.getName(),
                event.getStartDate(),
                event.getFinalDate(),
                event.getStreet(),
                event.getNumber(),
                event.getSupplement(),
                event.getNeighborhood(),
                event.getCity(),
                event.getState(),
                event.getZipCode(),
                games
        );
    }

}
