package com.projectLudoteca.ludoteca.command.model;

import java.time.LocalDate;

import com.projectLudoteca.ludoteca.common.enums.UserRole;

public record CreateUserCommand (String name, String cpf, String email, String password, String ra, LocalDate birthDate, UserRole userRole) {}
