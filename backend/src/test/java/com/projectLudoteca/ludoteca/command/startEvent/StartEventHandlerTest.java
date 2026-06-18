package com.projectLudoteca.ludoteca.command.startEvent;

import com.projectLudoteca.ludoteca.common.entity.Event;
import com.projectLudoteca.ludoteca.common.enums.EventStatus;
import com.projectLudoteca.ludoteca.common.exception.BusinessException;
import com.projectLudoteca.ludoteca.common.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StartEventHandlerTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private StartEventHandler handler;

    private String validEventIdString;
    private UUID validEventId;
    private Event dummyEvent;

    @BeforeEach
    void setUp() {
        validEventId = UUID.randomUUID();
        validEventIdString = validEventId.toString();

        dummyEvent = new Event();
        dummyEvent.setId(validEventId);
    }

    @Test
    @DisplayName("Deve lançar BusinessException quando o ID for inválido (malformado)")
    void should_ThrowException_When_IdIsInvalid() {
        assertThrows(BusinessException.class, () -> handler.handle("id-invalido-123"));

        verifyNoInteractions(eventRepository);
    }

    @Test
    @DisplayName("Deve lançar BusinessException quando o evento não for encontrado")
    void should_ThrowException_When_EventNotFound() {
        when(eventRepository.findByIdAndRemovedFalse(validEventId)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> handler.handle(validEventIdString));

        verify(eventRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar BusinessException quando o evento já estiver em andamento")
    void should_ThrowException_When_EventIsAlreadyInProgress() {
        dummyEvent.setStatus(EventStatus.INPROGRESS);
        when(eventRepository.findByIdAndRemovedFalse(validEventId)).thenReturn(Optional.of(dummyEvent));

        assertThrows(BusinessException.class, () -> handler.handle(validEventIdString));

        verify(eventRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar BusinessException quando o evento já estiver finalizado")
    void should_ThrowException_When_EventIsCompleted() {
        dummyEvent.setStatus(EventStatus.COMPLETED);
        when(eventRepository.findByIdAndRemovedFalse(validEventId)).thenReturn(Optional.of(dummyEvent));

        assertThrows(BusinessException.class, () -> handler.handle(validEventIdString));

        verify(eventRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve iniciar o evento com sucesso alterando o status para INPROGRESS")
    void should_StartEventSuccessfully_When_StatusIsScheduled() {
        dummyEvent.setStatus(EventStatus.SCHEDULED);
        when(eventRepository.findByIdAndRemovedFalse(validEventId)).thenReturn(Optional.of(dummyEvent));

        String response = handler.handle(validEventIdString);

        assertEquals("Evento iniciado com sucesso!", response);
        assertEquals(EventStatus.INPROGRESS, dummyEvent.getStatus());

        verify(eventRepository, times(1)).save(dummyEvent);
    }
}