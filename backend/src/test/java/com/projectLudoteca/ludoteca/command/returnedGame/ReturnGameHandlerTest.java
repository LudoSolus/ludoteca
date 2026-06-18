package com.projectLudoteca.ludoteca.command.returnedGame;

import com.projectLudoteca.ludoteca.common.entity.Game;
import com.projectLudoteca.ludoteca.common.entity.Loan;
import com.projectLudoteca.ludoteca.common.entity.User;
import com.projectLudoteca.ludoteca.common.enums.GameStatus;
import com.projectLudoteca.ludoteca.common.exception.BusinessException;
import com.projectLudoteca.ludoteca.common.repository.GameRepository;
import com.projectLudoteca.ludoteca.common.repository.LoanRepository;
import com.projectLudoteca.ludoteca.common.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReturnGameHandlerTest {

    @Mock
    private GameRepository gameRepository;
    @Mock
    private LoanRepository loanRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ReturnGameHandler handler;

    private String validGameIdString;
    private UUID validGameId;
    private Game dummyGame;
    private Loan dummyLoan;
    private User dummyUser;

    @BeforeEach
    void setUp() {
        validGameId = UUID.randomUUID();
        validGameIdString = validGameId.toString();

        dummyGame = new Game();
        dummyGame.setId(validGameId);
        dummyGame.setIsAvailable(false);

        dummyUser = new User();
        dummyUser.setId(UUID.randomUUID());
        dummyUser.setMinBoardGames(10);

        dummyLoan = new Loan();
        dummyLoan.setId(UUID.randomUUID());
        dummyLoan.setUserId(dummyUser.getId());
        dummyLoan.setGame(dummyGame);
        dummyLoan.setStatus(GameStatus.BORROWED);
        dummyLoan.setDateLoan(LocalDateTime.now().minusHours(2));
    }

    @Test
    @DisplayName("Deve lançar BusinessException quando o ID for inválido")
    void should_ThrowException_When_IdIsInvalid() {
        assertThrows(BusinessException.class, () -> handler.handle("id-invalido-123"));

        verifyNoInteractions(gameRepository, loanRepository, userRepository);
    }

    @Test
    @DisplayName("Deve lançar BusinessException quando o jogo não for encontrado")
    void should_ThrowException_When_GameNotFound() {
        when(gameRepository.findByIdAndRemovedFalse(validGameId)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> handler.handle(validGameIdString));

        verifyNoInteractions(loanRepository, userRepository);
        verify(gameRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar BusinessException quando não houver empréstimo ativo para o jogo")
    void should_ThrowException_When_ActiveLoanNotFound() {
        when(gameRepository.findByIdAndRemovedFalse(validGameId)).thenReturn(Optional.of(dummyGame));
        when(loanRepository.findByGameIdAndDateReturnIsNullAndRemovedFalse(validGameId)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> handler.handle(validGameIdString));

        verifyNoInteractions(userRepository);
        verify(loanRepository, never()).save(any());
        verify(gameRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar BusinessException quando o usuário do empréstimo não for encontrado")
    void should_ThrowException_When_UserNotFound() {
        when(gameRepository.findByIdAndRemovedFalse(validGameId)).thenReturn(Optional.of(dummyGame));
        when(loanRepository.findByGameIdAndDateReturnIsNullAndRemovedFalse(validGameId)).thenReturn(Optional.of(dummyLoan));
        when(userRepository.findByIdAndRemovedFalse(dummyLoan.getUserId())).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> handler.handle(validGameIdString));

        verify(loanRepository, never()).save(any());
        verify(gameRepository, never()).save(any());
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve realizar a devolução, alterar status e calcular o tempo jogado corretamente")
    void should_ReturnGameSuccessfully_And_CalculateMinutesPlayed() {
        when(gameRepository.findByIdAndRemovedFalse(validGameId)).thenReturn(Optional.of(dummyGame));
        when(loanRepository.findByGameIdAndDateReturnIsNullAndRemovedFalse(validGameId)).thenReturn(Optional.of(dummyLoan));
        when(userRepository.findByIdAndRemovedFalse(dummyLoan.getUserId())).thenReturn(Optional.of(dummyUser));

        String response = handler.handle(validGameIdString);

        // Asserções de Fluxo e Mutação
        assertEquals("Jogo devolvido com sucesso.", response);
        assertTrue(dummyGame.getIsAvailable(), "O jogo deve ficar disponível (true)");
        assertEquals(GameStatus.RETURNED, dummyLoan.getStatus(), "O status do empréstimo deve ser RETURNED");
        assertNotNull(dummyLoan.getDateReturn(), "A data de devolução deve ser preenchida");
        assertEquals(130, dummyUser.getMinBoardGames(), "O tempo jogado deve ser somado corretamente ao histórico do usuário");

        // Asserções de Persistência
        verify(loanRepository, times(1)).save(dummyLoan);
        verify(gameRepository, times(1)).save(dummyGame);
        verify(userRepository, times(1)).save(dummyUser);
    }
}