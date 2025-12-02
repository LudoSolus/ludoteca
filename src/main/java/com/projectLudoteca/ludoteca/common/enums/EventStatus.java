package com.projectLudoteca.ludoteca.common.enums;

public enum EventStatus {
    SCHEDULED("SCHEDULED"),
    INPROGRESS("INPROGRESS"),
    COMPLETED("COMPLETED");

    private String value;

    String getEventStatus() {
        return value;
    }

    EventStatus(String value) { this.value = value; }
}
