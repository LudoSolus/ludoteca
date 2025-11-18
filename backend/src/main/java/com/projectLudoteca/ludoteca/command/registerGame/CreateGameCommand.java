package com.projectLudoteca.ludoteca.command.registerGame;

import java.time.LocalDateTime;

public record CreateGameCommand(Integer barcode, String title, String category, String description, Integer minPlayers, Integer maxPlayers, String linkInstructionManual, String linkVideoTutorial) {
}
