package com.projectLudoteca.ludoteca.command.requestPasswordReset;

import com.projectLudoteca.ludoteca.common.entity.PasswordReset;
import com.projectLudoteca.ludoteca.common.entity.User;
import com.projectLudoteca.ludoteca.common.repository.PasswordResetRepository;
import com.projectLudoteca.ludoteca.common.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
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

    public void handle(RequestPasswordResetCommand command) {
        Optional<User> userOpt = userRepository.findByEmail(command.email());

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
            helper.setText("<p>Seu código de recuperação é:</p><h2>" + code + "</h2>", true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar e-mail de recuperação.");
        }
    }
}
