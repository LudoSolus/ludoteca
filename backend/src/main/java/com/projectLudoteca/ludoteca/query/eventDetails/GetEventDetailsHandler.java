package com.projectLudoteca.ludoteca.query.eventDetails;

import com.projectLudoteca.ludoteca.common.entity.Event;
import com.projectLudoteca.ludoteca.common.exception.BusinessException;
import com.projectLudoteca.ludoteca.common.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GetEventDetailsHandler {

    private EventRepository repository;

    public GetEventDetailsHandler(EventRepository repository) {
        this.repository = repository;
    }

    public GetEventDetailsView handle(String id) {

        UUID eventId;

        try {
            eventId = UUID.fromString(id);
        } catch (RuntimeException e) {
            throw new BusinessException("Id de evento inválido!");
        }

        Event event = repository.findByIdAndRemovedFalse(eventId)
                .orElseThrow(() -> new NoSuchElementException("Evento não encontrado."));

        List<GetEventDetailsView.ListGamesView> games = event.getGamesEvent().stream()
                .filter(ge -> !ge.getRemoved() && ge.getGame() != null)
                .map(ge -> new GetEventDetailsView.ListGamesView(
                        ge.getGame().getId(),
                        ge.getGame().getBarcode(),
                        ge.getGame().getTitle(),
                        ge.getGame().getIsAvailable(),
                        ge.getGame().getCategory().name(),
                        ge.getGame().getMinPlayers(),
                        ge.getGame().getMaxPlayers()))
                .collect(Collectors.toList());

        return new GetEventDetailsView(
                event.getName(),
                event.getDescription(),
                event.getStatus(),
                event.getStartDate(),
                event.getFinalDate(),
                event.getStreet(),
                event.getNumber(),
                event.getSupplement(),
                event.getNeighborhood(),
                event.getCity(),
                event.getState(),
                event.getZipCode(),
                event.getHasBoardGames(),
                event.getHasRpg(),
                event.getHasEscapeRoom(),
                games);
    }

}
