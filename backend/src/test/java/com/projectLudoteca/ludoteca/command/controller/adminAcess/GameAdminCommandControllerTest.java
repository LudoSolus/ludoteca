package com.projectLudoteca.ludoteca.command.controller.adminAcess;

import com.projectLudoteca.ludoteca.common.entity.Game;
import com.projectLudoteca.ludoteca.common.enums.GameCategory;
import com.projectLudoteca.ludoteca.common.repository.GameRepository;
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

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(scripts = "/data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class GameAdminCommandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GameRepository gameRepository;

    private UUID existingGameId;

    @BeforeEach
    void setUp() {
        gameRepository.deleteAll();

        Game game = new Game();

        game.setBarcode(123456);
        game.setTitle("Jogo Antigo");
        game.setCategory(GameCategory.STRATEGY);
        game.setDescription("Descrição Antiga");
        game.setMinPlayers(2);
        game.setMaxPlayers(4);
        game.setIsAvailable(true);
        game.setLinkInstructionManual("https://manual.com");
        game.setLinkVideoTutorial("https://video.com");
        game.setRemoved(false);

        game = gameRepository.save(game);

        this.existingGameId = game.getId();
    }

    // ENDPOINT /register

    @Test
    @WithMockUser(roles = "ADMIN")
    void should_ReturnOk_When_CreatingGameAsAdmin() throws Exception {

        String jsonPayload = """
                {
                  "barcode": 987654,
                  "title": "Catan",
                  "category": "STRATEGY",
                  "description": "Jogo de estratégia e negociação",
                  "minPlayers": 3,
                  "maxPlayers": 4,
                  "isAvailable": true,
                  "linkInstructionManual": "https://manual.com",
                  "linkVideoTutorial": "https://video.com"
                }
                """;

        mockMvc.perform(post("/commands/admin/games/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultData").value("Jogo cadastrado com sucesso!"));
    }

    @Test
    void should_ReturnUnauthorized_When_CreatingGameWithoutToken() throws Exception {

        String jsonPayload = """
                {
                  "barcode": 987654,
                  "title": "Catan",
                  "category": "STRATEGY",
                  "description": "Jogo de estratégia e negociação",
                  "minPlayers": 3,
                  "maxPlayers": 4,
                  "isAvailable": true,
                  "linkInstructionManual": "https://manual.com",
                  "linkVideoTutorial": "https://video.com"
                }
                """;

        mockMvc.perform(post("/commands/admin/games/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    void should_ReturnForbidden_When_CreatingGameAsUser() throws Exception {

        String jsonPayload = """
                {
                  "barcode": 987654,
                  "title": "Catan",
                  "category": "STRATEGY",
                  "description": "Jogo de estratégia e negociação",
                  "minPlayers": 3,
                  "maxPlayers": 4,
                  "isAvailable": true,
                  "linkInstructionManual": "https://manual.com",
                  "linkVideoTutorial": "https://video.com"
                }
                """;

        mockMvc.perform(post("/commands/admin/games/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void should_ReturnBadRequest_When_CreatingGameWithInvalidPayload() throws Exception {

        String jsonPayload = "{}";

        mockMvc.perform(post("/commands/admin/games/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isBadRequest());
    }

    // ENDPOINT /{id}/update

    @Test
    @WithMockUser(roles = "ADMIN")
    void should_ReturnOk_When_UpdatingGameAsAdmin() throws Exception {

        String jsonPayload = """
                {
                  "barcode": 987654,
                  "title": "Catan Atualizado",
                  "category": "STRATEGY",
                  "description": "Descrição atualizada do jogo",
                  "minPlayers": 2,
                  "maxPlayers": 5,
                  "isAvailable": true,
                  "linkInstructionManual": "https://manual-atualizado.com",
                  "linkVideoTutorial": "https://video-atualizado.com"
                }
                """;

        mockMvc.perform(put("/commands/admin/games/" + existingGameId + "/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultData").value("Jogo alterado com sucesso!"));
    }

    @Test
    void should_ReturnUnauthorized_When_UpdatingGameWithoutToken() throws Exception {

        String jsonPayload = """
                {
                  "barcode": 987654,
                  "title": "Catan",
                  "category": "STRATEGY",
                  "description": "Jogo de estratégia e negociação",
                  "minPlayers": 3,
                  "maxPlayers": 4,
                  "isAvailable": true,
                  "linkInstructionManual": "https://manual.com",
                  "linkVideoTutorial": "https://video.com"
                }
                """;

        mockMvc.perform(put("/commands/admin/games/" + existingGameId + "/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    void should_ReturnForbidden_When_UpdatingGameAsUser() throws Exception {

        String jsonPayload = """
                {
                  "barcode": 987654,
                  "title": "Catan",
                  "category": "STRATEGY",
                  "description": "Jogo de estratégia e negociação",
                  "minPlayers": 3,
                  "maxPlayers": 4,
                  "isAvailable": true,
                  "linkInstructionManual": "https://manual.com",
                  "linkVideoTutorial": "https://video.com"
                }
                """;

        mockMvc.perform(put("/commands/admin/games/" + existingGameId + "/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void should_ReturnBadRequest_When_UpdatingGameWithInvalidPayload() throws Exception {

        String jsonPayload = "{}";

        mockMvc.perform(put("/commands/admin/games/" + existingGameId + "/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isBadRequest());
    }
}