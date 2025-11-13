package com.projectLudoteca.ludoteca.command.registerUser;

import com.projectLudoteca.ludoteca.common.entity.EducationalInstitution;
import com.projectLudoteca.ludoteca.common.entity.User;
import com.projectLudoteca.ludoteca.common.enums.UserRole;
import com.projectLudoteca.ludoteca.common.exception.BusinessException;
import com.projectLudoteca.ludoteca.common.repository.EducationalInstitutionRepository;
import com.projectLudoteca.ludoteca.common.repository.UserRepository;
import com.projectLudoteca.ludoteca.infrastructure.security.config.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class CreateUserHandler {

    private final UserRepository repository;
    private final EducationalInstitutionRepository educationalInstitutionRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    public CreateUserHandler(UserRepository repository, EducationalInstitutionRepository educationalInstitutionRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.repository = repository;
        this.educationalInstitutionRepository = educationalInstitutionRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public String handle(CreateUserCommand command) {
        if (command.name() == null || command.name().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome é obrigatório.");
        }
        if (command.cpf() == null || command.cpf().trim().isEmpty()) {
            throw new IllegalArgumentException("O CPF é obrigatório.");
        }
        if (command.email() == null || command.email().trim().isEmpty()) {
            throw new IllegalArgumentException("O e-mail é obrigatório.");
        }
        if (command.password() == null || command.password().trim().isEmpty()) {
            throw new IllegalArgumentException("A senha é obrigatória.");
        }
        if (command.phone() == null || command.phone().trim().isEmpty()) {
            throw new IllegalArgumentException("O telefone é obrigatório.");
        }
        if( command.birthDate() == null ) {
            throw new IllegalArgumentException("A data de nascimento é obrigatória.");
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (!Pattern.matches(emailRegex, command.email())) {
            throw new IllegalArgumentException("Formato de e-mail inválido.");
        }



        String phoneRegex = "\\d{10,11}";
        if (!Pattern.matches(phoneRegex, command.phone())) {
            throw new IllegalArgumentException("Telefone inválido. Deve conter 10 ou 11 números.");
        }

        String senhaRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        if (!Pattern.matches(senhaRegex, command.password())) {
            throw new IllegalArgumentException("A senha deve conter no mínimo 8 caracteres, incluindo letras e números.");
        }

        if (command.ra() != null && !command.ra().matches("\\d+")) {
            throw new BusinessException("400", "O RA deve conter apenas números.");
        }

        if (repository.existsByEmail(command.email())) {
            throw new IllegalArgumentException("E-mail já cadastrado!");
        }
        if (repository.existsByCpf(command.cpf())) {
            throw new IllegalArgumentException("CPF já cadastrado!");
        }

        UserRole role = UserRole.USER;
        if(command.ra() != null && !command.ra().trim().isEmpty()) {
            role = UserRole.STUDENT;
            if (repository.existsByRa(command.ra())) {
                throw new IllegalArgumentException("RA já cadastrado!");
            }
        }

        String encodedPassword = passwordEncoder.encode(command.password());

        User user = new User();
        user.setName(command.name());
        user.setCpf(command.cpf());
        user.setEmail(command.email());
        user.setPassword(encodedPassword);
        user.setPhone(command.phone());
        user.setRa(command.ra());
        user.setBirthDate(command.birthDate());
        user.setUserRole(role);

        EducationalInstitution  educationalInstitution = new EducationalInstitution();

        if (command.institutionId() != null && !command.institutionId().isBlank()) {
            educationalInstitutionRepository.findById(UUID.fromString(command.institutionId()))
                    .ifPresent(user::setEducationalInstitution);
        }

        repository.save(user);

        String token = jwtService.generateToken(user.getId(), user.getName(),user.getPublicId(), user.getEmail(), user.getUserRole().name());

        return token;
    }
}
