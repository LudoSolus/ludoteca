package com.projectLudoteca.ludoteca.common.enums;

public enum UserRole {
    ADMIN("ADMIN"),
    STUDENT("STUDENT"),
    USER("USER");

    private String value;

    String getUserRole() {
        return value;
    }

    UserRole(String value) {
        this.value = value;
    }
}
