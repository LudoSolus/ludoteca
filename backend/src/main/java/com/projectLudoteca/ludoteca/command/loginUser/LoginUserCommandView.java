package com.projectLudoteca.ludoteca.command.loginUser;

import java.util.UUID;

public record LoginUserCommandView(UUID userId, String name, String token) {
}
