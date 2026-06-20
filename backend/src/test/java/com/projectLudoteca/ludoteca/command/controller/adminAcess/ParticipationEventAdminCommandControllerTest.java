package com.projectLudoteca.ludoteca.command.controller.adminAcess;

import com.projectLudoteca.ludoteca.command.registerParticipationEvent.CreateParticipationEventHandler;
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

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ParticipationEventAdminCommandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CreateParticipationEventHandler registerParticipationEventHandler;

    // ENDPOINT /register

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Deve registrar a participação com sucesso quando acessado por um ADMIN")
    void should_ReturnCreated_When_CreatingParticipationAsAdmin() throws Exception {
        Mockito.when(registerParticipationEventHandler.handle(any())).thenReturn("Participação registrada com sucesso!");

        String jsonPayload = """
        {
          "userPublicId": "id-publico-do-usuario-123",
          "eventId": "f4b0c531-90fa-4091-a602-bb049e794302"
        }
        """;

        mockMvc.perform(post("/commands/admin/participations-event/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.resultData").value("Participação registrada com sucesso!"));
    }

    @Test
    @DisplayName("Deve bloquear o registro da participação quando não houver autenticação (Anônimo)")
    void should_ReturnUnauthorized_When_CreatingParticipationWithoutToken() throws Exception {
        String jsonPayload = """
        {
          "userPublicId": "id-publico-do-usuario-123",
          "eventId": "f4b0c531-90fa-4091-a602-bb049e794302"
        }
        """;

        mockMvc.perform(post("/commands/admin/participations-event/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Deve bloquear o registro da participação quando acessado por um usuário comum (USER)")
    void should_ReturnForbidden_When_CreatingParticipationAsUser() throws Exception {
        String jsonPayload = """
        {
          "userPublicId": "id-publico-do-usuario-123",
          "eventId": "f4b0c531-90fa-4091-a602-bb049e794302"
        }
        """;

        mockMvc.perform(post("/commands/admin/participations-event/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Deve barrar requisição por falha estrutural ao enviar pacote de dados vazio")
    void should_ReturnBadRequest_When_CreatingParticipationWithInvalidPayload() throws Exception {
        mockMvc.perform(post("/commands/admin/participations-event/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());
    }
}