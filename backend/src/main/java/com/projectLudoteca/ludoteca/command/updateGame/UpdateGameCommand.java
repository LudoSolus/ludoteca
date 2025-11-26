package com.projectLudoteca.ludoteca.command.updateGame;

public record UpdateGameCommand(Integer barcode,
                                String title,
                                String category,
                                String description,
                                Integer minPlayers,
                                Integer maxPlayers,
                                String linkInstructionManual,
                                String linkVideoTutorial) {
}
