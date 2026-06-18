package com.projectLudoteca.ludoteca.command.controller.adminAcess;

import com.projectLudoteca.ludoteca.common.entity.User;
import com.projectLudoteca.ludoteca.common.enums.EventStatus;
import com.projectLudoteca.ludoteca.common.enums.UserRole;
import com.projectLudoteca.ludoteca.common.repository.EventRepository;
import com.projectLudoteca.ludoteca.common.repository.UserRepository;
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

import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(scripts = "/data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class UserAdminCommandControllerTest {

    // Injetando dependências
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    private UUID existingUserId;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        // Criando dados dinâmicos para o teste
        User user = new User();
        user.setName("Usuário Antigo");
        user.setCpf("11111111111");
        user.setEmail("usuarioantigo@usuario.com");
        user.setPhone("1111111111");
        user.setRa("1111111");
        user.setBirthDate(LocalDate.of(2000, 1, 1));
        user.setUserRole(UserRole.USER);
        user.setEducationalInstitution(null);

        user = userRepository.save(user);

        this.existingUserId = user.getId();
    }

    // ENDPOINT /register

    @Test
    @WithMockUser(roles = "ADMIN")
    void should_ReturnOk_When_CreatingUserAsAdmin() throws Exception {

        String jsonPayload = """
        {
          "name": "Novo Usuário",
          "cpf": "Alguma descrição",
          "email": "novousuario@usuario.com",
          "phone": "11111111111",
          "ra": "1111111",
          "birthDate": "2000-01-01T00:00:00",
          "userRole": "USER",
          "institutionId": "c3b0c531-90fa-4091-a602-bb049e794302"
        }
        """;

        mockMvc.perform(post("/commands/admin/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk());
    }
}