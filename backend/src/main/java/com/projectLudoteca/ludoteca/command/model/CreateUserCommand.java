package com.projectLudoteca.ludoteca.command.model;

public record CreateUserCommand (String name, String cpf, String email, String password, String ra ) {}
