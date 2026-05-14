package com.projectLudoteca.ludoteca.command.endEvent;

import com.projectLudoteca.ludoteca.common.entity.Event;
import com.projectLudoteca.ludoteca.common.enums.EventStatus;
import com.projectLudoteca.ludoteca.common.exception.BusinessException;
import com.projectLudoteca.ludoteca.common.repository.EventRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EndEventHandlerTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EndEventHandler endEventHandler;

    private final String validUuidString = "123e4567-e89b-12d3-a456-426614174000";
    private final UUID validUuid = UUID.fromString(validUuidString);

    @Test
    @DisplayName("Deve lançar exceção quando o ID do evento não for um UUID válido")
    void should_ThrowException_When_IdIsInvalid() {
        // Arrange
        String invalidId = "nao-sou-um-uuid";

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            endEventHandler.handle(invalidId);
        });

        assertEquals("Id de evento inválido!", exception.getMessage());

        verifyNoInteractions(eventRepository);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o evento não for encontrado no banco")
    void should_ThrowException_When_EventNotFound() {

        when(eventRepository.findByIdAndRemovedFalse(validUuid)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            endEventHandler.handle(validUuidString);
        });

        assertEquals("Evento não encontrado ou removido.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando tentar finalizar um evento com status SCHEDULED")
    void should_ThrowException_When_EventIsScheduled() {
        Event scheduledEvent = new Event();
        scheduledEvent.setStatus(EventStatus.SCHEDULED);

        when(eventRepository.findByIdAndRemovedFalse(validUuid)).thenReturn(Optional.of(scheduledEvent));

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            endEventHandler.handle(validUuidString);
        });

        assertEquals("Evento ainda não foi iniciado para ser finalizado!", exception.getMessage());

        // Verifica se o save não foi chamado
        verify(eventRepository, never()).save(any(Event.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando tentar finalizar um evento que já está COMPLETED")
    void should_ThrowException_When_EventIsAlreadyCompleted() {

        Event completedEvent = new Event();
        completedEvent.setStatus(EventStatus.COMPLETED);

        when(eventRepository.findByIdAndRemovedFalse(validUuid)).thenReturn(Optional.of(completedEvent));


        BusinessException exception = assertThrows(BusinessException.class, () -> {
            endEventHandler.handle(validUuidString);
        });

        assertEquals("Evento já foi finalizado!", exception.getMessage());
        verify(eventRepository, never()).save(any(Event.class));
    }

    @Test
    @DisplayName("Deve alterar status para COMPLETED e salvar no banco quando o evento estiver INPROGRESS")
    void should_EndEventSuccessfully_When_EventIsInprogress() {
        // Arrange
        Event inProgressEvent = new Event();
        inProgressEvent.setStatus(EventStatus.INPROGRESS);

        when(eventRepository.findByIdAndRemovedFalse(validUuid)).thenReturn(Optional.of(inProgressEvent));

        // Act
        String response = endEventHandler.handle(validUuidString);

        // Assert
        assertEquals("Evento finalizado com sucesso!", response);

        assertEquals(EventStatus.COMPLETED, inProgressEvent.getStatus());

        // Verifica se o "save" do repositório foi chamado exatamente 1 vez
        verify(eventRepository, times(1)).save(inProgressEvent);
    }
}