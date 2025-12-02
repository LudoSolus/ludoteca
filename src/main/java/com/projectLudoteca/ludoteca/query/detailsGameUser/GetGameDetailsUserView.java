package com.projectLudoteca.ludoteca.query.detailsGameUser;

import java.util.UUID;

public record GetGameDetailsUserView(UUID id,
                                     Integer barcode,
                                     String title,
                                     String description,
                                     String category,
                                     Integer minPlayers,
                                     Integer maxPlayers,
                                     String linkInstructionManual,
                                     String linkVideoTutorial) {
}
