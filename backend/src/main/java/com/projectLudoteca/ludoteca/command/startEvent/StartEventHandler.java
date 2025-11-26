package com.projectLudoteca.ludoteca.command.startEvent;

import ch.qos.logback.core.joran.event.StartEvent;
import com.projectLudoteca.ludoteca.common.entity.Event;
import com.projectLudoteca.ludoteca.common.enums.EventStatus;
import com.projectLudoteca.ludoteca.common.exception.BusinessException;
import com.projectLudoteca.ludoteca.common.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StartEventHandler {

    private final EventRepository eventRepository;

    public StartEventHandler(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public String handle(String id) {

        UUID eventId;

        try{
            eventId = UUID.fromString(id);
        } catch (RuntimeException e) {
            throw new BusinessException("Id de evento inválido!");
        }

        Event event = eventRepository.findByIdAndRemovedFalse(eventId).orElseThrow(() -> new BusinessException("Evento não encontrado ou removido."));

        if (event.getStatus() == EventStatus.INPROGRESS) {
            throw new BusinessException("Evento já foi iniciado!");
        }
        if (event.getStatus() == EventStatus.COMPLETED) {
            throw new BusinessException("Evento já foi finalizado e por isso não pode ser iniciado!");
        }

        event.setStatus(EventStatus.INPROGRESS);
        eventRepository.save(event);

        return "Evento iniciado com sucesso!";
    }

}
