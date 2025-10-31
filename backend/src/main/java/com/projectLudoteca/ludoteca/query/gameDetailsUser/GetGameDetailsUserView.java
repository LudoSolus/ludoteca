package com.projectLudoteca.ludoteca.query.gameDetailsUser;

public record GetGameDetailsUserView(String title,
                                     String description,
                                     String category,
                                     Integer minPlayers,
                                     Integer maxPlayers,
                                     String linkInstructionManual,
                                     String linkVideoTutorial) {
}
