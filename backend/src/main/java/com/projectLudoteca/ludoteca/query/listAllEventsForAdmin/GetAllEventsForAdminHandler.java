package com.projectLudoteca.ludoteca.query.listAllEventsForAdmin;

import com.projectLudoteca.ludoteca.common.entity.Event;
import com.projectLudoteca.ludoteca.common.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllEventsForAdminHandler {

    private final EventRepository eventRepository;

    public GetAllEventsForAdminHandler(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<GetAllEventsForAdminView> handle() {
        List<Event> events = eventRepository.findAll();

        return events.stream()
                .map(event -> new GetAllEventsForAdminView(
                        event.getId(),
                        event.getName(),
                        event.getStartDate(),
                        event.getFinalDate() != null ? event.getFinalDate().toString() : "Horário de finalização não definido",
                        event.getStatus(),
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
