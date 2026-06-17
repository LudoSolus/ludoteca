package com.projectLudoteca.ludoteca.command.controller.adminAcess;

import com.projectLudoteca.ludoteca.common.entity.Event;
import com.projectLudoteca.ludoteca.common.enums.EventStatus;
import com.projectLudoteca.ludoteca.common.repository.EventRepository;
import com.projectLudoteca.ludoteca.infrastructure.security.config.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(scripts = "/data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class EventAdminCommandControllerTest {

    // Injetando dependências
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private JwtService jwtService;

    private UUID existingEventId;

    @BeforeEach
    void setUp() {
        eventRepository.deleteAll();

        // Criando dados dinâmicos para o teste
        Event event = new Event();
        event.setName("Evento Antigo");
        event.setDescription("Descrição Antiga");
        event.setStartDate(LocalDateTime.now().plusDays(1));
        event.setFinalDate(LocalDateTime.now().plusDays(2));
        event.setStatus(EventStatus.SCHEDULED);
        event.setStreet("Rua X");
        event.setNumber("123");
        event.setNeighborhood("Centro");
        event.setCity("Cornélio Procópio");
        event.setState("PR");
        event.setZipCode("86300-000");
        event.setHasBoardGames(true);
        event.setHasRpg(false);
        event.setHasEscapeRoom(false);
        event.setRemoved(false);

        event = eventRepository.save(event);

        this.existingEventId = event.getId();
    }

    // ENDPOINT /register

    @Test
    @WithMockUser(roles = "ADMIN")
    void should_ReturnOk_When_CreatingEventAsAdmin() throws Exception {

        String jsonPayload = """
        {
          "name": "Novo Evento Criado 2026",
          "description": "Alguma descrição",
          "startDate": "2026-05-10T14:00:00",
          "finalDate": "2026-05-12T18:00:00",
          "street": "Rua Y",
          "number": "456",
          "neighborhood": "Vila Nova",
          "city": "Cornélio Procópio",
          "state": "PR",
          "zipCode": "86300-000",
          "hasBoardGames": true,
          "hasRpg": false,
          "hasEscapeRoom": false,
          "gamesIds": ["c3b0c531-90fa-4091-a602-bb049e794301"]
        }
        """;

        mockMvc.perform(post("/commands/admin/events/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultData").value("Evento cadastrado com Sucesso!"));
    }

    @Test
    void should_ReturnUnauthorized_When_CreatingEventWithoutToken() throws Exception {

        String jsonPayload = """
        {
          "name": "Novo Evento Criado 2026",
          "description": "Alguma descrição",
          "startDate": "2026-05-10T14:00:00",
          "finalDate": "2026-05-12T18:00:00",
          "street": "Rua Y",
          "number": "456",
          "neighborhood": "Vila Nova",
          "city": "Cornélio Procópio",
          "state": "PR",
          "zipCode": "86300-000",
          "hasBoardGames": true,
          "hasRpg": false,
          "hasEscapeRoom": false,
          "gamesIds": ["c3b0c531-90fa-4091-a602-bb049e794301"]
        }
        """;

        mockMvc.perform(post("/commands/admin/events/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    void should_ReturnForbidden_When_CreatingEventAsUser() throws Exception {

        String jsonPayload = """
        {
          "name": "Novo Evento Criado 2026",
          "description": "Alguma descrição",
          "startDate": "2026-05-10T14:00:00",
          "finalDate": "2026-05-12T18:00:00",
          "street": "Rua Y",
          "number": "456",
          "neighborhood": "Vila Nova",
          "city": "Cornélio Procópio",
          "state": "PR",
          "zipCode": "86300-000",
          "hasBoardGames": true,
          "hasRpg": false,
          "hasEscapeRoom": false,
          "gamesIds": ["c3b0c531-90fa-4091-a602-bb049e794301"]
        }
        """;

        mockMvc.perform(post("/commands/admin/events/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void should_ReturnBadRequest_When_CreatingEventWithInvalidPayload() throws Exception {
        String jsonPayload = "{}";

        mockMvc.perform(post("/commands/admin/events/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isBadRequest());
    }

    // ENDPOINT /{id}/update

    @Test
    @WithMockUser(roles = "ADMIN")
    void should_ReturnOk_When_UpdatingEventAsAdmin() throws Exception {

        String jsonPayload = """
        {
            "name": "Evento Atualizado 2026",
            "hasBoardGames": true,
            "gamesIds": ["c3b0c531-90fa-4091-a602-bb049e794301"]
        }
        """;

        mockMvc.perform(put("/commands/admin/events/" + existingEventId + "/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultData").value("Atualização realizada com sucesso!"));
    }

    @Test
    void should_ReturnUnauthorized_When_UpdatingEventWithoutToken() throws Exception {
        
        String jsonPayload = """
        {
            "name": "Evento Atualizado 2026",
            "hasBoardGames": true,
            "gamesIds": ["c3b0c531-90fa-4091-a602-bb049e794301"]
        }
        """;

        mockMvc.perform(put("/commands/admin/events/" + existingEventId + "/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    void should_ReturnForbidden_When_UpdatingEventAsUser() throws Exception {

        String jsonPayload = """
        {
            "name": "Evento Atualizado 2026",
            "hasBoardGames": true,
            "gamesIds": ["c3b0c531-90fa-4091-a602-bb049e794301"]
        }
        """;

        mockMvc.perform(put("/commands/admin/events/" + existingEventId + "/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void should_ReturnBadRequest_When_UpdatingEventWithInvalidPayload() throws Exception {
        // Testando o @Validated da classe
        String jsonPayload = "{}";

        mockMvc.perform(put("/commands/admin/events/" + existingEventId + "/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isBadRequest());
    }

    // ENDPOINT /{id}/start

    @Test
    @WithMockUser(roles = "ADMIN")
    void should_ReturnOk_When_StartingEventAsAdmin() throws Exception {

        mockMvc.perform(put("/commands/admin/events/" + existingEventId + "/start")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultData").value("Evento iniciado com sucesso!"));
    }

    @Test
    void should_ReturnUnauthorized_When_StartingEventWithoutToken() throws Exception {

        mockMvc.perform(put("/commands/admin/events/" + existingEventId + "/start")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    void should_ReturnForbidden_When_StartingEventAsUser() throws Exception {

        mockMvc.perform(put("/commands/admin/events/" + existingEventId + "/start")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    // ENDPOINT /{id}/end

    @Test
    @WithMockUser(roles = "ADMIN")
    void should_ReturnOk_When_EndingEventAsAdmin() throws Exception {
        // Deixando o evento em progresso para simular a finalização de um evento
        Event eventToComplete = eventRepository.findById(existingEventId).orElseThrow();
        eventToComplete.setStatus(EventStatus.INPROGRESS);
        eventRepository.save(eventToComplete);

        mockMvc.perform(put("/commands/admin/events/" + existingEventId + "/end")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultData").value("Evento finalizado com sucesso!"));
    }

    @Test
    void should_ReturnUnauthorized_When_EndingEventWithoutToken() throws Exception {

        mockMvc.perform(put("/commands/admin/events/" + existingEventId + "/end")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    void should_ReturnForbidden_When_EndingEventAsUser() throws Exception {

        mockMvc.perform(put("/commands/admin/events/" + existingEventId + "/end")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}