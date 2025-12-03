package com.projectLudoteca.ludoteca.command.loginUser;

import jakarta.validation.constraints.Email;

public record LoginUserCommand (@Email(message = "Email inválido") String email, String password) {}
