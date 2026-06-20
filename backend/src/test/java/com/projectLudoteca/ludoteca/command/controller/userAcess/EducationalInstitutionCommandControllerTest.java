package com.projectLudoteca.ludoteca.command.controller.userAcess;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectLudoteca.ludoteca.command.registerEducationalInstitution.CreateEducationalInstitutionCommand;
import com.projectLudoteca.ludoteca.common.repository.EducationalInstitutionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EducationalInstitutionCommandControllerTest {

    // Injetando dependências
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EducationalInstitutionRepository educationalInstitutionRepository;

    @BeforeEach
    void setUp() {
        educationalInstitutionRepository.deleteAll();
    }

    // ENDPOINT /register
    @Test
    @DisplayName("Deve registrar uma nova instituição com sucesso")
    void should_RegisterEducationalInstitutionSuccessfully() throws Exception {
        CreateEducationalInstitutionCommand command = new CreateEducationalInstitutionCommand("UTFPR - Campus Cornélio Procópio");

        mockMvc.perform(post("/commands/educational-institutions/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.resultData").value("Instituição registrada com sucesso"));

        assertEquals(1, educationalInstitutionRepository.count());
    }

    @Test
    @DisplayName("Deve falhar ao tentar registrar uma instituição com nome vazio")
    void should_FailToRegister_When_NameIsEmpty() throws Exception {
        CreateEducationalInstitutionCommand command = new CreateEducationalInstitutionCommand("   ");

        mockMvc.perform(post("/commands/educational-institutions/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isBadRequest());

        assertEquals(0, educationalInstitutionRepository.count());
    }
}