package com.projectLudoteca.ludoteca.command.confirmationPasswordReset;

import jakarta.validation.constraints.NotBlank;

public record ConfirmationPasswordResetCommand(
        @NotBlank(message = "Código é obrigatório") String code,
        @NotBlank(message = "Nova senha é obrigatória") String newPassword
) {}
