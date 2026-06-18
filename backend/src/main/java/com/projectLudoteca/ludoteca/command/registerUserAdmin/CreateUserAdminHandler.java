package com.projectLudoteca.ludoteca.command.registerUserAdmin;

import com.projectLudoteca.ludoteca.common.entity.EducationalInstitution;
import com.projectLudoteca.ludoteca.common.entity.User;
import com.projectLudoteca.ludoteca.common.enums.UserRole;
import com.projectLudoteca.ludoteca.common.exception.BusinessException;
import com.projectLudoteca.ludoteca.common.repository.EducationalInstitutionRepository;
import com.projectLudoteca.ludoteca.common.repository.UserRepository;
import com.projectLudoteca.ludoteca.common.util.PasswordGenerator;
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
    private final PasswordGenerator passwordGenerator;
    private final JavaMailSender mailSender;

    @Autowired
    public CreateUserAdminHandler(UserRepository repository, EducationalInstitutionRepository educationalInstitutionRepository, PasswordEncoder passwordEncoder, PasswordGenerator passwordGenerator, JavaMailSender mailSender) {
        this.repository = repository;
        this.educationalInstitutionRepository = educationalInstitutionRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordGenerator = passwordGenerator;
        this.mailSender = mailSender;
    }

    @Transactional
    public String handle(CreateUserAdminCommand command) {
        validateInputs(command);

        if (repository.existsByEmail(command.email())) {
            throw new BusinessException("400", "E-mail já cadastrado!");
        }
        if (repository.existsByCpf(command.cpf())) {
            throw new BusinessException("400", "CPF já cadastrado!");
        }

        boolean raPresente = command.ra() != null && !command.ra().trim().isEmpty();
        boolean institutionIdPresente = command.institutionId() != null && !command.institutionId().isBlank();

        if (raPresente && !institutionIdPresente) {
            throw new BusinessException("400", "A Instituição Educacional é obrigatória quando o RA é fornecido.");
        }
        if (institutionIdPresente && !raPresente) {
            throw new BusinessException("400", "O RA é obrigatório quando a Instituição Educacional é fornecida.");
        }
        if (raPresente && repository.existsByRa(command.ra())) {
            throw new BusinessException("400", "RA já cadastrado!");
        }

        UserRole role = command.userRole() != null ? command.userRole() : (raPresente ? UserRole.STUDENT : UserRole.USER);
        EducationalInstitution institution = raPresente ? fetchEducationalInstitution(command.institutionId(), true) : null;

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

        return "Usuário criado com sucesso. Senha enviada por e-mail.";
    }

    private void validateInputs(CreateUserAdminCommand command) {
        if (command.name() == null || command.name().trim().isEmpty()) throw new BusinessException("400", "O nome é obrigatório.");
        if (command.cpf() == null || command.cpf().trim().isEmpty()) throw new BusinessException("400", "O CPF é obrigatório.");
        if (command.email() == null || command.email().trim().isEmpty()) throw new BusinessException("400", "O e-mail é obrigatório.");
        if (command.phone() == null || command.phone().trim().isEmpty()) throw new BusinessException("400", "O telefone é obrigatório.");
        if (command.birthDate() == null) throw new BusinessException("400", "A data de nascimento é obrigatória.");

        if (!Pattern.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", command.email())) throw new BusinessException("400", "Formato de e-mail inválido.");
        if (!Pattern.matches("\\d{10,11}", command.phone())) throw new BusinessException("400", "Telefone inválido. Deve conter 10 ou 11 números.");
        if (command.ra() != null && !command.ra().matches("\\d+")) throw new BusinessException("400", "O RA deve conter apenas números.");
    }

    private EducationalInstitution fetchEducationalInstitution(String institutionId, boolean required) {
        if (required && (institutionId == null || institutionId.isBlank())) {
            throw new BusinessException("400", "O ID da Instituição Educacional é obrigatório.");
        }
        if (institutionId == null || institutionId.isBlank()) return null;

        try {
            return educationalInstitutionRepository.findById(UUID.fromString(institutionId))
                    .orElseThrow(() -> new BusinessException("404", "Instituição Educacional não encontrada."));
        } catch (IllegalArgumentException e) {
            throw new BusinessException("400", "Formato do ID da instituição inválido.");
        }
    }

    private void sendRecoveryEmail(String to, String password) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject("Sua Senha - Ludoteca");
            helper.setText("<!DOCTYPE html>\n" +
                    "<html lang=\"pt-BR\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\" />\n" +
                    "    <style>\n" +
                    "        body {\n" +
                    "            font-family: 'Georgia', serif;\n" +
                    "            background-color: #faf6e8;\n" +
                    "            margin: 0;\n" +
                    "            padding: 0;\n" +
                    "        }\n" +
                    "        .container {\n" +
                    "            max-width: 550px;\n" +
                    "            margin: 40px auto;\n" +
                    "            background: #ffffff;\n" +
                    "            border-radius: 16px;\n" +
                    "            padding: 30px;\n" +
                    "            border: 3px solid #f4d85a;\n" +
                    "        }\n" +
                    "        .header {\n" +
                    "            text-align: center;\n" +
                    "            background: #f4d85a;\n" +
                    "            padding: 20px;\n" +
                    "            border-radius: 12px;\n" +
                    "            font-size: 24px;\n" +
                    "            font-weight: bold;\n" +
                    "            color: #4a3b00;\n" +
                    "        }\n" +
                    "        .icon-area {\n" +
                    "            margin-top: 25px;\n" +
                    "            text-align: center;\n" +
                    "        }\n" +
                    "        .icon-circle {\n" +
                    "            background-color: #5b3ef0;\n" +
                    "            width: 120px;\n" +
                    "            height: 120px;\n" +
                    "            border-radius: 50%;\n" +
                    "            display: flex;\n" +
                    "            margin: auto;\n" +
                    "            align-items: center;\n" +
                    "            justify-content: center;\n" +
                    "            font-size: 70px;\n" +
                    "            color: #f4d85a;\n" +
                    "            padding-left: 3px;\n" +
                    "        }\n" +
                    "        .content {\n" +
                    "            margin-top: 25px;\n" +
                    "            font-size: 18px;\n" +
                    "            color: #333;\n" +
                    "            line-height: 1.6;\n" +
                    "        }\n" +
                    "        .password-box {\n" +
                    "            margin: 25px auto;\n" +
                    "            background: #5b3ef0;\n" +
                    "            padding: 15px 25px;\n" +
                    "            text-align: center;\n" +
                    "            width: fit-content;\n" +
                    "            border-radius: 12px;\n" +
                    "            font-size: 28px;\n" +
                    "            font-weight: bold;\n" +
                    "            color: #ffffff;\n" +
                    "            border: 3px solid #3626a7;\n" +
                    "        }\n" +
                    "        .footer {\n" +
                    "            margin-top: 35px;\n" +
                    "            text-align: center;\n" +
                    "            font-size: 14px;\n" +
                    "            color: #777;\n" +
                    "        }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<div class=\"container\">\n" +
                    "\n" +
                    "    <div class=\"header\">Bem-vindo à Ludoteca!</div>\n" +
                    "\n" +
                    "    <div class=\"icon-area\">\n" +
                    "        <div class=\"icon-circle\">\uD83D\uDEE1\uFE0F</div>\n" +
                    "    </div>\n" +
                    "\n" +
                    "    <div class=\"content\">\n" +
                    "        Sua conta foi criada por um Administrador da <strong>Ludoteca</strong>!<br><br>\n" +
                    "        Aqui está sua senha inicial:\n" +
                    "    </div>\n" +
                    "\n" +
                    "    <div class=\"password-box\">\n" +
                    "        " + password + "\n" +
                    "    </div>\n" +
                    "\n" +
                    "    <div class=\"content\">\n" +
                    "        Recomendamos que você altere a senha ao fazer login pela primeira vez.<br>\n" +
                    "        Boas partidas e divirta-se! \uD83C\uDFB2⚔\uFE0F\n" +
                    "    </div>\n" +
                    "\n" +
                    "    <div class=\"footer\">\n" +
                    "        Ludoteca UTFPR — Onde a diversão começa!\n" +
                    "    </div>\n" +
                    "\n" +
                    "</div>\n" +
                    "</body>\n" +
                    "</html>\n", true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new BusinessException("500", "Erro ao enviar e-mail de recuperação.");
        }
    }
}