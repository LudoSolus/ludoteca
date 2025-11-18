package com.projectLudoteca.ludoteca.command.confirmationPasswordReset;

import com.projectLudoteca.ludoteca.common.entity.PasswordReset;
import com.projectLudoteca.ludoteca.common.entity.User;
import com.projectLudoteca.ludoteca.common.exception.BusinessException;
import com.projectLudoteca.ludoteca.common.repository.PasswordResetRepository;
import com.projectLudoteca.ludoteca.common.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

@Service
public class ConfirmationPasswordResetHandler {

    private final PasswordResetRepository passwordResetRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ConfirmationPasswordResetHandler(PasswordResetRepository passwordResetRepository,
                                UserRepository userRepository,
                                PasswordEncoder passwordEncoder) {
        this.passwordResetRepository = passwordResetRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String handle(ConfirmationPasswordResetCommand command) {

        PasswordReset passwordReset = passwordResetRepository.findByCode(command.code())
                .orElseThrow(() -> new BusinessException("Código de recuperação inválido."));

        if (passwordReset.isUsed() || passwordReset.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new BusinessException("Código expirado ou já utilizado.");
        }

        User user = passwordReset.getUser();
        user.getPasswordResets().add(passwordReset);

        if (passwordEncoder.matches(command.newPassword(), user.getPassword())) {
            throw new BusinessException("A nova senha não pode ser igual à senha atual.");
        }

        String encodedPassword = passwordEncoder.encode(command.newPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);

        passwordReset.setUsed(true);
        passwordResetRepository.save(passwordReset);

        return "Senha redefinida com sucesso.";
    }
}
