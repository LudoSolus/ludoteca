package com.projectLudoteca.ludoteca.command.loanGameInEvent;

import com.projectLudoteca.ludoteca.common.entity.Event;
import com.projectLudoteca.ludoteca.common.entity.Game;
import com.projectLudoteca.ludoteca.common.entity.Loan;
import com.projectLudoteca.ludoteca.common.entity.User;
import com.projectLudoteca.ludoteca.common.entity.id.GameEventId;
import com.projectLudoteca.ludoteca.common.exception.BusinessException;
import com.projectLudoteca.ludoteca.common.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanGameInEventHandlerTest {

    @Mock
    private LoanRepository loanRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private GameRepository gameRepository;
    @Mock
    private EventRepository eventRepository;
    @Mock
    private GameEventRepository gameEventRepository;

    @InjectMocks
    private LoanGameInEventHandler handler;

    private LoanGameInEventCommand validCommand;
    private User dummyUser;
    private Game dummyGame;
    private Event dummyEvent;

    @BeforeEach
    void setUp() {
        UUID gameId = UUID.randomUUID();
        UUID eventId = UUID.randomUUID();
        String userPublicId = "user-public-123";

        validCommand = new LoanGameInEventCommand(userPublicId, gameId, eventId);

        dummyUser = new User();
        dummyGame = new Game();
        dummyGame.setId(gameId);
        dummyGame.setIsAvailable(true);
        dummyEvent = new Event();
    }

    @Test
    @DisplayName("Deve lançar BusinessException quando o usuário não for encontrado")
    void should_ThrowException_When_UserNotFound() {
        when(userRepository.findByPublicIdAndRemovedFalse(validCommand.userPublicId())).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> handler.handle(validCommand));

        verifyNoInteractions(gameRepository, eventRepository, gameEventRepository, loanRepository);
    }

    @Test
    @DisplayName("Deve lançar BusinessException quando o jogo não for encontrado")
    void should_ThrowException_When_GameNotFound() {
        when(userRepository.findByPublicIdAndRemovedFalse(validCommand.userPublicId())).thenReturn(Optional.of(dummyUser));
        when(gameRepository.findByIdAndRemovedFalse(validCommand.gameId())).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> handler.handle(validCommand));

        verifyNoInteractions(eventRepository, gameEventRepository, loanRepository);
    }

    @Test
    @DisplayName("Deve lançar BusinessException quando o jogo não pertencer ao evento")
    void should_ThrowException_When_GameIsNotInEvent() {
        when(userRepository.findByPublicIdAndRemovedFalse(validCommand.userPublicId())).thenReturn(Optional.of(dummyUser));
        when(gameRepository.findByIdAndRemovedFalse(validCommand.gameId())).thenReturn(Optional.of(dummyGame));
        when(eventRepository.findByIdAndRemovedFalse(validCommand.eventId())).thenReturn(Optional.of(dummyEvent));
        when(gameEventRepository.existsById(any(GameEventId.class))).thenReturn(false);

        assertThrows(BusinessException.class, () -> handler.handle(validCommand));

        verify(loanRepository, never()).save(any());
        verify(gameRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar BusinessException quando o jogo já estiver emprestado (empréstimo ativo)")
    void should_ThrowException_When_GameIsAlreadyLoaned() {
        when(userRepository.findByPublicIdAndRemovedFalse(validCommand.userPublicId())).thenReturn(Optional.of(dummyUser));
        when(gameRepository.findByIdAndRemovedFalse(validCommand.gameId())).thenReturn(Optional.of(dummyGame));
        when(eventRepository.findByIdAndRemovedFalse(validCommand.eventId())).thenReturn(Optional.of(dummyEvent));
        when(gameEventRepository.existsById(any(GameEventId.class))).thenReturn(true);
        when(loanRepository.existsByGameIdAndDateReturnIsNullAndRemovedFalse(validCommand.gameId())).thenReturn(true);

        assertThrows(BusinessException.class, () -> handler.handle(validCommand));

        verify(loanRepository, never()).save(any());
        verify(gameRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve realizar o empréstimo com sucesso alterando a disponibilidade do jogo")
    void should_LoanGameSuccessfully_When_AllConditionsAreMet() {
        when(userRepository.findByPublicIdAndRemovedFalse(validCommand.userPublicId())).thenReturn(Optional.of(dummyUser));
        when(gameRepository.findByIdAndRemovedFalse(validCommand.gameId())).thenReturn(Optional.of(dummyGame));
        when(eventRepository.findByIdAndRemovedFalse(validCommand.eventId())).thenReturn(Optional.of(dummyEvent));
        when(gameEventRepository.existsById(any(GameEventId.class))).thenReturn(true);
        when(loanRepository.existsByGameIdAndDateReturnIsNullAndRemovedFalse(validCommand.gameId())).thenReturn(false);

        String result = handler.handle(validCommand);

        assertEquals("Jogo emprestado com sucesso!", result);
        assertFalse(dummyGame.getIsAvailable(), "A flag do jogo deve ser alterada para indisponível (false)");

        verify(gameRepository, times(1)).save(dummyGame);
        verify(loanRepository, times(1)).save(any(Loan.class));
    }
}