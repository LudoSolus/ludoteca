package com.projectLudoteca.ludoteca.command.registerUser;

import java.time.LocalDate;

import com.projectLudoteca.ludoteca.common.enums.UserRole;

public record CreateUserCommand (String name, String cpf, String email, String password, String phone, String ra, LocalDate birthDate, UserRole userRole, String institutionId) {
}
