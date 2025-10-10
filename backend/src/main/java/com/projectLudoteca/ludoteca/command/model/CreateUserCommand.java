package com.projectLudoteca.ludoteca.command.model;

public record CreateUserCommand (String name, String cpf, String password, String email, String ra ) {}
