package com.projectLudoteca.ludoteca.command.controller.userAcess;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectLudoteca.ludoteca.command.confirmationPasswordReset.ConfirmationPasswordResetCommand;
import com.projectLudoteca.ludoteca.command.registerUser.CreateUserCommand;
import com.projectLudoteca.ludoteca.command.requestPasswordReset.RequestPasswordResetCommand;
import com.projectLudoteca.ludoteca.command.updateUser.UpdateUserCommand;
import com.projectLudoteca.ludoteca.common.entity.PasswordReset;
import com.projectLudoteca.ludoteca.common.entity.User;
import com.projectLudoteca.ludoteca.common.enums.UserRole;
import com.projectLudoteca.ludoteca.common.repository.EducationalInstitutionRepository;
import com.projectLudoteca.ludoteca.common.repository.PasswordResetRepository;
import com.projectLudoteca.ludoteca.common.repository.UserRepository;
import com.projectLudoteca.ludoteca.infrastructure.security.config.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import jakarta.mail.internet.MimeMessage;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(properties = "management.health.mail.enabled=false")
class UserCommandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetRepository passwordResetRepository;

    @Autowired
    private EducationalInstitutionRepository educationalInstitutionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @MockitoBean
    private JavaMailSender javaMailSender;

    private User savedUser;

    @BeforeEach
    void setUp() {
        passwordResetRepository.deleteAll();
        userRepository.deleteAll();
        educationalInstitutionRepository.deleteAll();

        // Criando dados dinâmicos para o teste
        savedUser = new User();
        savedUser.setName("Usuario Teste");
        savedUser.setCpf("12345678909");
        savedUser.setEmail("teste@ludoteca.com");
        savedUser.setPassword(passwordEncoder.encode("SenhaForte123"));
        savedUser.setPhone("11999999999");
        savedUser.setBirthDate(LocalDate.of(2000, 1, 1));
        savedUser.setUserRole(UserRole.USER);
        userRepository.save(savedUser);
    }

    // ENDPOINT /register
    @Test
    @DisplayName("Deve registrar um usuário com sucesso e retornar 201 Created")
    void should_RegisterUserSuccessfully() throws Exception {
        CreateUserCommand command = new CreateUserCommand(
                "Novo Usuario",
                "98765432100",
                "novo@ludoteca.com",
                "SenhaForte123",
                "11988888888",
                null,
                LocalDate.of(1995, 5, 10),
                UserRole.USER,
                null
        );

        mockMvc.perform(post("/commands/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.resultData").isNotEmpty());

        assertTrue(userRepository.existsByEmail("novo@ludoteca.com"));
    }

    @Test
    @DisplayName("Deve falhar ao registrar usuário com e-mail já existente retornando 400")
    void should_FailToRegister_When_EmailAlreadyExists() throws Exception {
        CreateUserCommand command = new CreateUserCommand(
                "Outro Usuario",
                "11122233344",
                savedUser.getEmail(),
                "SenhaForte123",
                "11988888888",
                null,
                LocalDate.of(1995, 5, 10),
                UserRole.USER,
                null
        );

        mockMvc.perform(post("/commands/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isBadRequest());
    }

    // ENDPOINT /login
    @Test
    @DisplayName("Deve realizar login com sucesso e retornar Token JWT")
    void should_LoginSuccessfully() throws Exception {
        String loginJson = """
                {
                    "email": "teste@ludoteca.com",
                    "password": "SenhaForte123"
                }
                """;

        mockMvc.perform(post("/commands/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultData").isNotEmpty());
    }

    // ENDPOINT /request-password-reset
    @Test
    @DisplayName("Deve solicitar recuperação de senha com sucesso")
    void should_RequestPasswordResetSuccessfully() throws Exception {
        when(javaMailSender.createMimeMessage()).thenReturn(Mockito.mock(MimeMessage.class));

        RequestPasswordResetCommand command = new RequestPasswordResetCommand(savedUser.getEmail());

        mockMvc.perform(post("/commands/users/request-password-reset")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultData").value("Se existir uma conta com este e-mail, um código foi enviado."));

        assertEquals(1, passwordResetRepository.count());
    }

    // ENDPOINT /confirmation-password-reset
    @Test
    @DisplayName("Deve confirmar a nova senha com código válido")
    void should_ConfirmPasswordResetSuccessfully() throws Exception {
        PasswordReset reset = new PasswordReset();
        reset.setUserId(savedUser.getId());
        reset.setUser(savedUser);
        reset.setEmail(savedUser.getEmail());
        reset.setCode("123456");
        reset.setCreatedAt(LocalDateTime.now());
        reset.setExpiresAt(LocalDateTime.now().plusMinutes(10));
        reset.setUsed(false);
        passwordResetRepository.save(reset);

        ConfirmationPasswordResetCommand command = new ConfirmationPasswordResetCommand("123456", "NovaSenhaMestra456");

        mockMvc.perform(post("/commands/users/confirmation-password-reset")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultData").value("Senha redefinida com sucesso."));

        User updatedUser = userRepository.findById(savedUser.getId()).orElseThrow();
        assertTrue(passwordEncoder.matches("NovaSenhaMestra456", updatedUser.getPassword()));
    }

    // ENDPOINT /update
    @Test
    @DisplayName("Deve atualizar os dados do usuário logado")
    void should_UpdateUserSuccessfully() throws Exception {
        String validToken = jwtService.generateToken(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getPublicId(),
                savedUser.getEmail(),
                savedUser.getUserRole().name()
        );

        UpdateUserCommand command = new UpdateUserCommand("Nome Atualizado", null, "43999999999", null, null);

        mockMvc.perform(put("/commands/users/update")
                        .header("Authorization", "Bearer " + validToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultData").isNotEmpty());

        User updatedUser = userRepository.findById(savedUser.getId()).orElseThrow();
        assertEquals("Nome Atualizado", updatedUser.getName());
        assertEquals("43999999999", updatedUser.getPhone());
    }
}