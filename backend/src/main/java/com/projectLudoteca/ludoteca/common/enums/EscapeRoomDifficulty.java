package com.projectLudoteca.ludoteca.common.enums;

public enum EscapeRoomDifficulty {
    EASY("EASY"),
    NORMAL("NORMAL"),
    HARD("HARD");

    private String value;

    String getEscapeRoomDifficulty() {
        return value;
    }

    EscapeRoomDifficulty(String value) { this.value = value; }
}
