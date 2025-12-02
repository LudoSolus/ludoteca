package com.projectLudoteca.ludoteca.command.updateUser;

import jakarta.validation.constraints.Email;

public record UpdateUserCommand(String name, @Email(message = "Email inválido") String email, String phone, String ra, String institutionId) {
}
