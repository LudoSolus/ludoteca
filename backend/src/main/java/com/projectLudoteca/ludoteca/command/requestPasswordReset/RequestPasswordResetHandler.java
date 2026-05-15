package com.projectLudoteca.ludoteca.command.requestPasswordReset;

import com.projectLudoteca.ludoteca.common.entity.PasswordReset;
import com.projectLudoteca.ludoteca.common.entity.User;
import com.projectLudoteca.ludoteca.common.repository.PasswordResetRepository;
import com.projectLudoteca.ludoteca.common.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RequestPasswordResetHandler {

    private final UserRepository userRepository;
    private final PasswordResetRepository passwordResetRepository;
    private final JavaMailSender mailSender;

    public RequestPasswordResetHandler(UserRepository userRepository, PasswordResetRepository passwordResetRepository , JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.passwordResetRepository = passwordResetRepository;
        this.mailSender = mailSender;
    }

    @Transactional
    public void handle(RequestPasswordResetCommand command) {
        Optional<User> userOpt = userRepository.findByEmailAndRemovedFalse(command.email());

        userOpt.ifPresent(user -> {
            boolean hasActiveCode = passwordResetRepository
                    .findTopByUserAndUsedFalseOrderByCreatedAtDesc(user)
                    .filter(pr -> pr.getExpiresAt().isAfter(LocalDateTime.now()))
                    .isPresent();

            if (hasActiveCode) {
                return;
            }
            String code = generateRecoveryCode();
            sendRecoveryEmail(user.getEmail(), code);

            PasswordReset passwordReset = new PasswordReset();
            passwordReset.setUserId(user.getId());
            passwordReset.setUser(user);
            passwordReset.setEmail(user.getEmail());
            passwordReset.setCode(code);
            passwordReset.setCreatedAt(LocalDateTime.now());
            passwordReset.setExpiresAt(LocalDateTime.now().plusMinutes(10));
            passwordReset.setUsed(false);

            passwordResetRepository.save(passwordReset);
        });
    }

    private String generateRecoveryCode() {
        SecureRandom random = new SecureRandom();
        int code = 100000 + random.nextInt(900000); // 6 dígitos
        return String.valueOf(code);
    }

    private void sendRecoveryEmail(String to, String code) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject("Recuperação de Senha - Ludoteca");
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
                    "            transform: translateX(3px);\n" +
                    "        }\n" +
                    "        .content {\n" +
                    "            margin-top: 25px;\n" +
                    "            font-size: 18px;\n" +
                    "            color: #333;\n" +
                    "            line-height: 1.6;\n" +
                    "        }\n" +
                    "        .code-box {\n" +
                    "            margin: 25px auto;\n" +
                    "            background: #f4d85a;\n" +
                    "            padding: 15px 25px;\n" +
                    "            text-align: center;\n" +
                    "            width: fit-content;\n" +
                    "            border-radius: 12px;\n" +
                    "            font-size: 28px;\n" +
                    "            font-weight: bold;\n" +
                    "            color: #4a3b00;\n" +
                    "            border: 3px solid #4a3b00;\n" +
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
                    "    <div class=\"header\">Recuperação de Senha</div>\n" +
                    "\n" +
                    "    <div class=\"icon-area\">\n" +
                    "        <div class=\"icon-circle\">\uD83D\uDD12</div>\n" +
                    "    </div>\n" +
                    "\n" +
                    "    <div class=\"content\">\n" +
                    "        Olá! <br><br>\n" +
                    "        Recebemos uma solicitação para redefinir sua senha da <strong>Ludoteca</strong>.<br>\n" +
                    "        Use o código abaixo para continuar o processo:\n" +
                    "    </div>\n" +
                    "\n" +
                    "    <div class=\"code-box\">\n" +
                    "        " + code +"\n" +
                    "    </div>\n" +
                    "\n" +
                    "    <div class=\"content\">\n" +
                    "        Se você não solicitou essa recuperação, pode ignorar este e-mail com segurança.\n" +
                    "    </div>\n" +
                    "\n" +
                    "    <div class=\"footer\">\n" +
                    "        Ludoteca UTFPR — Jogos, Estratégia e Diversão \uD83C\uDFB2\n" +
                    "    </div>\n" +
                    "\n" +
                    "</div>\n" +
                    "</body>\n" +
                    "</html>\n", true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar e-mail de recuperação.");
        }
    }
}
