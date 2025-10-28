package com.projectLudoteca.ludoteca.command.model;

public record UpdateUserCommand(String name, String email, String phone, String ra, String institutionId) {
}
