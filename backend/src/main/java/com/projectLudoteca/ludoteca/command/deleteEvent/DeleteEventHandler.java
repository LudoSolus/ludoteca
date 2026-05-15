package com.projectLudoteca.ludoteca.command.deleteEvent;

import com.projectLudoteca.ludoteca.common.entity.Event;
import com.projectLudoteca.ludoteca.common.enums.EventStatus;
import com.projectLudoteca.ludoteca.common.exception.BusinessException;
import com.projectLudoteca.ludoteca.common.repository.EventRepository;
import com.projectLudoteca.ludoteca.common.repository.LoanRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class DeleteEventHandler {

    private final EventRepository eventRepository;
    private final LoanRepository loanRepository;

    public DeleteEventHandler(EventRepository eventRepository, LoanRepository loanRepository) {
        this.eventRepository = eventRepository;
        this.loanRepository = loanRepository;
    }

    @Transactional
    public String handle(String eventId) {
        // Validar se o ID do evento é válido
        UUID eventIdUuid;
        try {
            eventIdUuid = UUID.fromString(eventId);
        } catch (RuntimeException e) {
            throw new BusinessException("EVT_001", "Id de evento inválido!");
        }

        // Buscar o evento
        Event eventToDelete = eventRepository.findByIdAndRemovedFalse(eventIdUuid)
                .orElseThrow(() -> new BusinessException("EVT_002", "Evento não encontrado ou já foi removido."));

        // Validar se o evento já foi removido
        if (eventToDelete.getRemoved()) {
            throw new BusinessException("EVT_003", "Este evento já foi removido.");
        }

        // Validar se o evento foi finalizado (não pode remover eventos finalizados)
        if (eventToDelete.getStatus() == EventStatus.COMPLETED) {
            throw new BusinessException("EVT_004", "Não é possível remover um evento que já foi finalizado.");
        }

        // Validar se o evento possui empréstimos ativos
        if (loanRepository.hasActiveLoansByEventId(eventIdUuid)) {
            throw new BusinessException("EVT_005", "Não é possível remover um evento com empréstimos ativos.");
        }

        // Realizar exclusão lógica
        eventToDelete.setRemoved(true);
        eventToDelete.setDeletedAt(LocalDateTime.now());

        eventRepository.save(eventToDelete);

        return "Evento removido com sucesso!";
    }
}
