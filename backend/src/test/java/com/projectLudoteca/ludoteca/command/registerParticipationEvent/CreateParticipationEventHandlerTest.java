package com.projectLudoteca.ludoteca.command.registerParticipationEvent;

import com.projectLudoteca.ludoteca.common.entity.Event;
import com.projectLudoteca.ludoteca.common.entity.ParticipationEvent;
import com.projectLudoteca.ludoteca.common.entity.User;
import com.projectLudoteca.ludoteca.common.enums.EventStatus;
import com.projectLudoteca.ludoteca.common.exception.BusinessException;
import com.projectLudoteca.ludoteca.common.repository.EventRepository;
import com.projectLudoteca.ludoteca.common.repository.ParticipationEventRepository;
import com.projectLudoteca.ludoteca.common.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateParticipationEventHandlerTest {

    @Mock
    private ParticipationEventRepository participationEventRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EventRepository eventRepository;

    private CreateParticipationEventHandler handler;

    private String userPublicId;
    private UUID eventId;
    private CreateParticipationEventCommand command;

    @BeforeEach
    void setUp() {
        handler = new CreateParticipationEventHandler(
                participationEventRepository,
                userRepository,
                eventRepository
        );

        userPublicId = UUID.randomUUID().toString();
        eventId = UUID.randomUUID();

        command = new CreateParticipationEventCommand(userPublicId, eventId);
    }

    @Test
    void should_ThrowBusinessException_When_UserNotFound() {
        when(userRepository.findByPublicIdAndRemovedFalse(userPublicId))
                .thenReturn(Optional.empty());

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> handler.handle(command)
        );

        assertEquals("Usuário não encontrado", exception.getMessage());

        verify(userRepository, times(1)).findByPublicIdAndRemovedFalse(userPublicId);
        verifyNoInteractions(eventRepository);
        verifyNoInteractions(participationEventRepository);
    }

    @Test
    void should_ThrowBusinessException_When_EventNotFound() {
        User user = new User();

        when(userRepository.findByPublicIdAndRemovedFalse(userPublicId))
                .thenReturn(Optional.of(user));

        when(eventRepository.findByIdAndRemovedFalse(eventId))
                .thenReturn(Optional.empty());

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> handler.handle(command)
        );

        assertEquals("Evento não encontrado.", exception.getMessage());

        verify(userRepository, times(1)).findByPublicIdAndRemovedFalse(userPublicId);
        verify(eventRepository, times(1)).findByIdAndRemovedFalse(eventId);
        verifyNoInteractions(participationEventRepository);
    }

    @Test
    void should_ThrowBusinessException_When_EventIsScheduled() {
        User user = new User();

        Event event = new Event();
        event.setStatus(EventStatus.SCHEDULED);

        when(userRepository.findByPublicIdAndRemovedFalse(userPublicId))
                .thenReturn(Optional.of(user));

        when(eventRepository.findByIdAndRemovedFalse(eventId))
                .thenReturn(Optional.of(event));

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> handler.handle(command)
        );

        assertEquals("O evento não está em andamento para registrar presença.", exception.getMessage());

        verify(userRepository, times(1)).findByPublicIdAndRemovedFalse(userPublicId);
        verify(eventRepository, times(1)).findByIdAndRemovedFalse(eventId);
        verify(participationEventRepository, never()).existsByEventAndUser(any(Event.class), any(User.class));
        verify(participationEventRepository, never()).save(any(ParticipationEvent.class));
    }

    @Test
    void should_ThrowBusinessException_When_EventIsCompleted() {
        User user = new User();

        Event event = new Event();
        event.setStatus(EventStatus.COMPLETED);

        when(userRepository.findByPublicIdAndRemovedFalse(userPublicId))
                .thenReturn(Optional.of(user));

        when(eventRepository.findByIdAndRemovedFalse(eventId))
                .thenReturn(Optional.of(event));

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> handler.handle(command)
        );

        assertEquals("O evento não está em andamento para registrar presença.", exception.getMessage());

        verify(userRepository, times(1)).findByPublicIdAndRemovedFalse(userPublicId);
        verify(eventRepository, times(1)).findByIdAndRemovedFalse(eventId);
        verify(participationEventRepository, never()).existsByEventAndUser(any(Event.class), any(User.class));
        verify(participationEventRepository, never()).save(any(ParticipationEvent.class));
    }

    @Test
    void should_ThrowBusinessException_When_ParticipationAlreadyExists() {
        User user = new User();

        Event event = new Event();
        event.setStatus(EventStatus.INPROGRESS);

        when(userRepository.findByPublicIdAndRemovedFalse(userPublicId))
                .thenReturn(Optional.of(user));

        when(eventRepository.findByIdAndRemovedFalse(eventId))
                .thenReturn(Optional.of(event));

        when(participationEventRepository.existsByEventAndUser(event, user))
                .thenReturn(true);

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> handler.handle(command)
        );

        assertEquals("Presença já registrada para este usuário neste evento.", exception.getMessage());

        verify(userRepository, times(1)).findByPublicIdAndRemovedFalse(userPublicId);
        verify(eventRepository, times(1)).findByIdAndRemovedFalse(eventId);
        verify(participationEventRepository, times(1)).existsByEventAndUser(event, user);
        verify(participationEventRepository, never()).save(any(ParticipationEvent.class));
    }

    @Test
    void should_SaveParticipation_When_UserAndEventAreValidAndEventIsInProgress() {
        User user = new User();

        Event event = new Event();
        event.setStatus(EventStatus.INPROGRESS);

        when(userRepository.findByPublicIdAndRemovedFalse(userPublicId))
                .thenReturn(Optional.of(user));

        when(eventRepository.findByIdAndRemovedFalse(eventId))
                .thenReturn(Optional.of(event));

        when(participationEventRepository.existsByEventAndUser(event, user))
                .thenReturn(false);

        String result = handler.handle(command);

        assertEquals("Presença registrada com sucesso!", result);

        ArgumentCaptor<ParticipationEvent> participationCaptor =
                ArgumentCaptor.forClass(ParticipationEvent.class);

        verify(participationEventRepository, times(1))
                .save(participationCaptor.capture());

        ParticipationEvent participationSaved = participationCaptor.getValue();

        assertNotNull(participationSaved);
        assertEquals(event, participationSaved.getEvent());
        assertEquals(user, participationSaved.getUser());

        verify(userRepository, times(1)).findByPublicIdAndRemovedFalse(userPublicId);
        verify(eventRepository, times(1)).findByIdAndRemovedFalse(eventId);
        verify(participationEventRepository, times(1)).existsByEventAndUser(event, user);
    }
}