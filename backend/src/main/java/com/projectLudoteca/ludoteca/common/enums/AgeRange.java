package com.projectLudoteca.ludoteca.common.enums;

public enum AgeRange {
    ALL("Livre"),
    AGE_10("Não recomendado para menores de 10 anos"),
    AGE_12("Não recomendado para menores de 12 anos"),
    AGE_14("Não recomendado para menores de 14 anos"),
    AGE_16("Não recomendado para menores de 16 anos"),
    AGE_18("Não recomendado para menores de 18 anos");

    private String value;

    String getAgeRange() {
        return value;
    }

    AgeRange(String value) { this.value = value; }
}
