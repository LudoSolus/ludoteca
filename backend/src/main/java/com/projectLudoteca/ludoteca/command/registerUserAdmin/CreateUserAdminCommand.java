package com.projectLudoteca.ludoteca.command.registerUserAdmin;

import com.projectLudoteca.ludoteca.common.enums.UserRole;
import com.projectLudoteca.ludoteca.common.validation.Cpf;
import jakarta.validation.constraints.Email;

import java.time.LocalDate;

public record CreateUserAdminCommand(String name, @Cpf String cpf, @Email(message = "Email inválido") String email, String phone, String ra, LocalDate birthDate, UserRole userRole, String institutionId) {
}
