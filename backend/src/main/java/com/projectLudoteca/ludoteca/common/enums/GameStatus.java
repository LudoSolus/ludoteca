package com.projectLudoteca.ludoteca.common.enums;

public enum GameStatus {
    BORROWED("BORROWED"),
    RETURNED("RETURNED");

    private String value;

    String getGameStatus() {
        return value;
    }

    GameStatus(String value) { this.value = value; }
}
