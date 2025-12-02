package com.projectLudoteca.ludoteca.command.registerUser;

import java.time.LocalDate;

import com.projectLudoteca.ludoteca.common.enums.UserRole;
import com.projectLudoteca.ludoteca.common.validation.Cpf;
import jakarta.validation.constraints.Email;

public record CreateUserCommand (String name, @Cpf String cpf, @Email(message = "Email inválido") String email, String password, String phone, String ra, LocalDate birthDate, UserRole userRole, String institutionId) {
}
