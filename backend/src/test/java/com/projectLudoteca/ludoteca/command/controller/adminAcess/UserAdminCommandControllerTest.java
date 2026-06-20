package com.projectLudoteca.ludoteca.command.controller.adminAcess;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectLudoteca.ludoteca.command.changeRoleUser.ChangeRoleUserCommand;
import com.projectLudoteca.ludoteca.command.registerUserAdmin.CreateUserAdminCommand;
import com.projectLudoteca.ludoteca.common.entity.User;
import com.projectLudoteca.ludoteca.common.enums.UserRole;
import com.projectLudoteca.ludoteca.common.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.mail.internet.MimeMessage;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(properties = "management.health.mail.enabled=false")
class UserAdminCommandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @MockitoBean
    private JavaMailSender mailSender;

    private User existingUser;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        // Configura o Mock do Email para não estourar NullPointerException
        when(mailSender.createMimeMessage()).thenReturn(mock(MimeMessage.class));

        existingUser = new User();
        existingUser.setName("Usuário Teste Antigo");
        existingUser.setCpf("00000000000");
        existingUser.setEmail("antigo@teste.com");
        existingUser.setPassword("senha123");
        existingUser.setPhone("11999999999");
        existingUser.setBirthDate(LocalDate.of(1990, 1, 1));
        existingUser.setUserRole(UserRole.USER);
        existingUser = userRepository.save(existingUser);
    }

    // ENDPOINT /register

    @Test
    @WithMockUser(roles = "ADMIN")
    void should_ReturnCreated_AndSaveToDb_When_AdminRegistersUser() throws Exception {
        CreateUserAdminCommand validCommand = new CreateUserAdminCommand(
                "Lucas Silva", "12345678909", "lucas@email.com", "11999999999",
                null, LocalDate.of(2000, 1, 1), UserRole.USER, null
        );

        mockMvc.perform(post("/commands/admin/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validCommand)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.resultData").value("Usuário criado com sucesso. Senha enviada por e-mail."));

        assertTrue(userRepository.existsByEmail("lucas@email.com"));
    }

    @Test
    @WithAnonymousUser
    void should_ReturnUnauthorized_When_AnonymousTriesToRegisterUser() throws Exception {
        mockMvc.perform(post("/commands/admin/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    void should_ReturnForbidden_When_CommonUserTriesToRegisterUser() throws Exception {
        mockMvc.perform(post("/commands/admin/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void should_ReturnBadRequest_When_PayloadIsInvalid() throws Exception {
        CreateUserAdminCommand invalidCommand = new CreateUserAdminCommand(
                "Lucas Silva", "12345678909", "email-invalido", "11999999999",
                null, LocalDate.of(2000, 1, 1), UserRole.USER, null
        );

        mockMvc.perform(post("/commands/admin/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidCommand)))
                .andExpect(status().isBadRequest());
    }

    // ENDPOINT /{id}/change-role

    @Test
    @WithMockUser(roles = "ADMIN")
    void should_ReturnCreated_AndUpdateDb_When_AdminChangesUserRole() throws Exception {
        ChangeRoleUserCommand command = new ChangeRoleUserCommand(true);

        mockMvc.perform(post("/commands/admin/users/" + existingUser.getId() + "/change-role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isCreated());

        User updatedUser = userRepository.findById(existingUser.getId()).orElseThrow();
        assertEquals(UserRole.ADMIN, updatedUser.getUserRole());
    }

    @Test
    @WithMockUser(roles = "USER")
    void should_ReturnForbidden_When_CommonUserTriesToChangeRole() throws Exception {
        mockMvc.perform(post("/commands/admin/users/" + existingUser.getId() + "/change-role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"isAdmin\": true}"))
                .andExpect(status().isForbidden());
    }
}