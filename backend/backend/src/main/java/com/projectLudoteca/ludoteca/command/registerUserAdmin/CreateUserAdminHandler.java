package com.projectLudoteca.ludoteca.command.registerUserAdmin;

import com.projectLudoteca.ludoteca.common.entity.EducationalInstitution;
import com.projectLudoteca.ludoteca.common.entity.User;
import com.projectLudoteca.ludoteca.common.enums.UserRole;
import com.projectLudoteca.ludoteca.common.exception.BusinessException;
import com.projectLudoteca.ludoteca.common.repository.EducationalInstitutionRepository;
import com.projectLudoteca.ludoteca.common.repository.UserRepository;
import com.projectLudoteca.ludoteca.common.util.PasswordGenerator;
import com.projectLudoteca.ludoteca.infrastructure.security.config.JwtService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class CreateUserAdminHandler {

    private final UserRepository repository;
    private final EducationalInstitutionRepository educationalInstitutionRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final PasswordGenerator passwordGenerator;
    private final JavaMailSender mailSender;

    @Autowired
    public CreateUserAdminHandler(UserRepository repository, EducationalInstitutionRepository educationalInstitutionRepository, PasswordEncoder passwordEncoder, JwtService jwtService, PasswordGenerator passwordGenerator, JavaMailSender mailSender) {
        this.repository = repository;
        this.educationalInstitutionRepository = educationalInstitutionRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.passwordGenerator = passwordGenerator;
        this.mailSender = mailSender;
    }

    @Transactional
    public String handle(CreateUserAdminCommand command) {
        if (command.name() == null || command.name().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome é obrigatório.");
        }
        if (command.cpf() == null || command.cpf().trim().isEmpty()) {
            throw new IllegalArgumentException("O CPF é obrigatório.");
        }
        if (command.email() == null || command.email().trim().isEmpty()) {
            throw new IllegalArgumentException("O e-mail é obrigatório.");
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

        if (command.ra() != null && !command.ra().matches("\\d+")) {
            throw new BusinessException("400", "O RA deve conter apenas números.");
        }

        if (repository.existsByEmail(command.email())) {
            throw new IllegalArgumentException("E-mail já cadastrado!");
        }
        if (repository.existsByCpf(command.cpf())) {
            throw new IllegalArgumentException("CPF já cadastrado!");
        }

        UserRole role;
        EducationalInstitution institution = null;

        boolean raPresente = command.ra() != null && !command.ra().trim().isEmpty();
        boolean institutionIdPresente = command.institutionId() != null && !command.institutionId().isBlank();

        if (raPresente && !institutionIdPresente) {
            throw new IllegalArgumentException("A Instituição Educacional é obrigatória quando o Registro Acadêmico (RA) é fornecido.");
        }

        if (institutionIdPresente && !raPresente) {
            throw new IllegalArgumentException("O Registro Acadêmico (RA) é obrigatório quando a Instituição Educacional é fornecida.");
        }

        if (command.userRole() == UserRole.ADMIN) {
            role = UserRole.ADMIN;

            if (raPresente && repository.existsByRa(command.ra())) {
                throw new IllegalArgumentException("RA já cadastrado!");
            }

            if (raPresente) {
                UUID institutionUuid;
                try {
                    institutionUuid = UUID.fromString(command.institutionId());
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Formato do ID da instituição inválido.");
                }

                institution = educationalInstitutionRepository.findById(institutionUuid)
                        .orElseThrow(() -> new BusinessException("404", "Instituição Educacional não encontrada."));
            }
        } else if (raPresente) {
            role = UserRole.STUDENT;

            if (repository.existsByRa(command.ra())) {
                throw new IllegalArgumentException("RA já cadastrado!");
            }

            UUID institutionUuid;
            try {
                institutionUuid = UUID.fromString(command.institutionId());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Formato do ID da instituição inválido.");
            }

            institution = educationalInstitutionRepository.findById(institutionUuid)
                    .orElseThrow(() -> new BusinessException("404", "Instituição Educacional não encontrada."));
        } else {
            role = UserRole.USER;
        }

        String password = passwordGenerator.generate();

        sendRecoveryEmail(command.email(), password);

        String encodedPassword = passwordEncoder.encode(password);

        User user = new User();
        user.setName(command.name());
        user.setCpf(command.cpf());
        user.setEmail(command.email());
        user.setPassword(encodedPassword);
        user.setPhone(command.phone());
        user.setRa(command.ra());
        user.setBirthDate(command.birthDate());
        user.setUserRole(role);
        user.setEducationalInstitution(institution);

        repository.save(user);

        String token = jwtService.generateToken(user.getId(), user.getName(),user.getPublicId(), user.getEmail(), user.getUserRole().name());

        return token;
    }

    private EducationalInstitution fetchEducationalInstitution(String institutionId, boolean required) {
        if (required && (institutionId == null || institutionId.isBlank())) {
            throw new IllegalArgumentException("O ID da Instituição Educacional é obrigatório.");
        }

        if (institutionId == null || institutionId.isBlank()) {
            return null;
        }

        UUID institutionUuid;
        try {
            institutionUuid = UUID.fromString(institutionId);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Formato do ID da instituição inválido.");
        }

        return educationalInstitutionRepository.findById(institutionUuid)
                .orElseThrow(() -> new BusinessException("404", "Instituição Educacional não encontrada."));
    }

    private void sendRecoveryEmail(String to, String password) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject("Sua Senha - Ludoteca");
            helper.setText("<p>Sua senha é:</p><h2>" + password + "</h2>", true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar e-mail de recuperação.");
        }
    }
}
