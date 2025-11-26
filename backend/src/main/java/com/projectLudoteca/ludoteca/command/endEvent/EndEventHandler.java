package com.projectLudoteca.ludoteca.command.endEvent;

import com.projectLudoteca.ludoteca.common.entity.Event;
import com.projectLudoteca.ludoteca.common.enums.EventStatus;
import com.projectLudoteca.ludoteca.common.exception.BusinessException;
import com.projectLudoteca.ludoteca.common.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EndEventHandler {

    private final EventRepository eventRepository;

    public EndEventHandler(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public String handle(String id) {

        UUID uuid;

        try{
            uuid = UUID.fromString(id);
        } catch (RuntimeException e) {
            throw new BusinessException("Id de evento inválido!");
        }

        Event event = eventRepository.findByIdAndRemovedFalse(uuid).orElseThrow(() -> new BusinessException("Evento não encontrado ou removido."));

        if (event.getStatus() == EventStatus.SCHEDULED) {
            throw new BusinessException("Evento ainda não foi iniciado para ser finalizado!");
        }
        if (event.getStatus() == EventStatus.COMPLETED) {
            throw new BusinessException("Evento já foi finalizado!");
        }

        event.setStatus(EventStatus.COMPLETED);
        eventRepository.save(event);

        return "Evento finalizado com sucesso!";
    }

}
