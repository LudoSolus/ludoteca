package com.projectLudoteca.ludoteca.query.nextEvents;

import com.projectLudoteca.ludoteca.common.entity.Event;
import com.projectLudoteca.ludoteca.common.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GetNextEventsHandler {

    private final EventRepository repository;

    public GetNextEventsHandler(EventRepository repository) {
        this.repository = repository;
    }

    public List<GetNextEventsView> handle() {

        List<Event> events = repository
                .findByRemovedFalseAndStartDateAfterOrderByStartDateAsc(LocalDateTime.now());

        return events.stream()
                .map(event -> new GetNextEventsView(
                        event.getId(),
                        event.getName(),
                        event.getStartDate(),
                        event.getFinalDate() != null ? event.getFinalDate().toString() : "Horário de finalização não definido",
                        event.getStreet(),
                        event.getNumber(),
                        event.getNeighborhood(),
                        event.getCity(),
                        event.getState(),
                        event.getZipCode()
                ))
                .toList();
    }

}
