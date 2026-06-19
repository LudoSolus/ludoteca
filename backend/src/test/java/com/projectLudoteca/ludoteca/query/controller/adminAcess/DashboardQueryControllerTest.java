package com.projectLudoteca.ludoteca.query.controller.adminAcess;

import com.projectLudoteca.ludoteca.common.repository.LoanRepository;
import com.projectLudoteca.ludoteca.common.repository.ParticipationEventRepository;
import com.projectLudoteca.ludoteca.common.repository.UserRepository;
import com.projectLudoteca.ludoteca.query.dashboard.*;
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

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class DashboardQueryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ParticipationEventRepository participationEventRepository;

    @MockitoBean
    private LoanRepository loanRepository;

    @MockitoBean
    private UserRepository userRepository;

    // ENDPOINT /participants-by-event

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Deve atravessar o fluxo e retornar dados de participantes quando acessado por ADMIN")
    void should_ReturnParticipantsData_When_Admin() throws Exception {
        List<ParticipantsByEventData> mockData = List.of(new ParticipantsByEventData("Noite de Jogos 2026", 150L));
        Mockito.when(participationEventRepository.countParticipantsByEvent()).thenReturn(mockData);

        mockMvc.perform(get("/queries/admin/dashboard/participants-by-event")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultData.data[0].eventName").value("Noite de Jogos 2026"))
                .andExpect(jsonPath("$.resultData.data[0].participantCount").value(150L));
    }

    @Test
    @DisplayName("Deve bloquear a requisição de participantes na barreira de segurança para anônimos")
    void should_ReturnUnauthorized_OnParticipantsData_When_Anonymous() throws Exception {
        mockMvc.perform(get("/queries/admin/dashboard/participants-by-event")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Deve bloquear a requisição de participantes na barreira de segurança para usuários comuns")
    void should_ReturnForbidden_OnParticipantsData_When_User() throws Exception {
        mockMvc.perform(get("/queries/admin/dashboard/participants-by-event")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    // ENDPOINT /most-played-games

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Deve atravessar o fluxo e retornar dados de jogos mais jogados quando acessado por ADMIN")
    void should_ReturnMostPlayedGamesData_When_Admin() throws Exception {
        List<MostPlayedGamesData> mockData = List.of(new MostPlayedGamesData("Elden Ring Board Game", 300L));
        Mockito.when(loanRepository.countMostPlayedGames()).thenReturn(mockData);

        mockMvc.perform(get("/queries/admin/dashboard/most-played-games")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultData.data[0].gameTitle").value("Elden Ring Board Game"))
                .andExpect(jsonPath("$.resultData.data[0].loanCount").value(300L));
    }

    @Test
    @DisplayName("Deve bloquear a requisição de jogos mais jogados para anônimos")
    void should_ReturnUnauthorized_OnMostPlayedGames_When_Anonymous() throws Exception {
        mockMvc.perform(get("/queries/admin/dashboard/most-played-games")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Deve bloquear a requisição de jogos mais jogados para usuários comuns")
    void should_ReturnForbidden_OnMostPlayedGames_When_User() throws Exception {
        mockMvc.perform(get("/queries/admin/dashboard/most-played-games")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    // ENDPOINT /registered-users-count

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Deve atravessar o fluxo e retornar a contagem de usuários quando acessado por ADMIN")
    void should_ReturnUsersCountData_When_Admin() throws Exception {
        Mockito.when(userRepository.countByRemovedFalse()).thenReturn(1500L);

        mockMvc.perform(get("/queries/admin/dashboard/registered-users-count")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultData.totalUsers").value(1500L));
    }

    @Test
    @DisplayName("Deve bloquear a requisição de contagem de usuários para anônimos")
    void should_ReturnUnauthorized_OnUsersCount_When_Anonymous() throws Exception {
        mockMvc.perform(get("/queries/admin/dashboard/registered-users-count")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Deve bloquear a requisição de contagem de usuários para usuários comuns")
    void should_ReturnForbidden_OnUsersCount_When_User() throws Exception {
        mockMvc.perform(get("/queries/admin/dashboard/registered-users-count")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}