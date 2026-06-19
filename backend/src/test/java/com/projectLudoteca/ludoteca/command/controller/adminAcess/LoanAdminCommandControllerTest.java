package com.projectLudoteca.ludoteca.command.controller.adminAcess;

import com.projectLudoteca.ludoteca.command.loanGameInEvent.LoanGameInEventHandler;
import com.projectLudoteca.ludoteca.command.returnedGame.ReturnGameHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class LoanAdminCommandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LoanGameInEventHandler loanGameInEventHandler;

    @MockitoBean
    private ReturnGameHandler returnGameHandler;

    // ENDPOINT /loan-event

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Deve registrar empréstimo com sucesso quando acessado por um ADMIN")
    void should_CreateLoanSuccessfully_When_Admin() throws Exception {
        Mockito.when(loanGameInEventHandler.handle(any())).thenReturn("Jogo emprestado com sucesso!");

        String jsonPayload = """
        {
          "userPublicId": "algum-id-publico-123",
          "gameId": "c3b0c531-90fa-4091-a602-bb049e794301",
          "eventId": "f4b0c531-90fa-4091-a602-bb049e794302"
        }
        """;

        mockMvc.perform(post("/commands/admin/loans/loan-event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultData").value("Jogo emprestado com sucesso!"));
    }

    @Test
    @DisplayName("Deve bloquear registro de empréstimo quando não houver autenticação (Anônimo)")
    void should_RejectCreateLoan_When_Anonymous() throws Exception {
        String jsonPayload = """
        {
          "userPublicId": "algum-id-publico-123",
          "gameId": "c3b0c531-90fa-4091-a602-bb049e794301",
          "eventId": "f4b0c531-90fa-4091-a602-bb049e794302"
        }
        """;

        mockMvc.perform(post("/commands/admin/loans/loan-event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Deve bloquear registro de empréstimo quando acessado por um usuário comum (USER)")
    void should_RejectCreateLoan_When_UserRole() throws Exception {
        String jsonPayload = """
        {
          "userPublicId": "algum-id-publico-123",
          "gameId": "c3b0c531-90fa-4091-a602-bb049e794301",
          "eventId": "f4b0c531-90fa-4091-a602-bb049e794302"
        }
        """;

        mockMvc.perform(post("/commands/admin/loans/loan-event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Deve barrar requisição de empréstimo por falha estrutural ao enviar corpo vazio")
    void should_RejectCreateLoan_When_PayloadEmpty() throws Exception {
        mockMvc.perform(post("/commands/admin/loans/loan-event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());
    }

    // ENDPOINT /{id}/return-game

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Deve processar devolução com sucesso quando acessado por um ADMIN")
    void should_ReturnGameSuccessfully_When_Admin() throws Exception {
        Mockito.when(returnGameHandler.handle(any())).thenReturn("Jogo devolvido com sucesso!");

        mockMvc.perform(post("/commands/admin/loans/" + UUID.randomUUID() + "/return-game")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultData").value("Jogo devolvido com sucesso!"));
    }

    @Test
    @DisplayName("Deve bloquear devolução quando não houver autenticação (Anônimo)")
    void should_RejectReturnGame_When_Anonymous() throws Exception {
        mockMvc.perform(post("/commands/admin/loans/" + UUID.randomUUID() + "/return-game")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Deve bloquear devolução quando acessado por um usuário comum (USER)")
    void should_RejectReturnGame_When_UserRole() throws Exception {
        mockMvc.perform(post("/commands/admin/loans/" + UUID.randomUUID() + "/return-game")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}