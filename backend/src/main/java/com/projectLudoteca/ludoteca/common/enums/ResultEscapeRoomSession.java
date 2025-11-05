package com.projectLudoteca.ludoteca.common.enums;

public enum ResultEscapeRoomSession {
    SUCCESS("SUCCESS"),
    FAILURE("FAILURE"),
    NOT_COMPLETED("NOT_COMPLETED");

    private String value;

    String getResultEscapeRoomSession() {
        return value;
    }

    ResultEscapeRoomSession(String value) { this.value = value; }
}
