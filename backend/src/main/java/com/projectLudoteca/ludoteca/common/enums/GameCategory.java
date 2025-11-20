package com.projectLudoteca.ludoteca.common.enums;

public enum GameCategory {

    STRATEGY("Strategy"),
    COOPERATIVE("Cooperative"),
    DEDUCTION("Deduction"),
    PARTY_GAME("Party Game"),
    BLUFF_DECEPTION("Bluff and Deception"),
    DEXTERITY("Dexterity"),
    ABSTRACT_GAMES("Abstract Games"),
    LOGIC_PUZZLE("Logic and Puzzle"),
    ADVENTURE_THEME("Adventure and Thematic"),
    NEGOTIATION("Negotiation"),
    OTHER("Other");

    private String value;

    String getGameCategory() {
        return value;
    }

    GameCategory(String value) { this.value = value; }

}
