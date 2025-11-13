package com.projectLudoteca.ludoteca.command.registerUser;

import java.util.UUID;

public record CreateUserCommandView(UUID userId, String name, String token) {
}
