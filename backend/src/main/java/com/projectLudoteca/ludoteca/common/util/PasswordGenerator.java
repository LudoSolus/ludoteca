package com.projectLudoteca.ludoteca.common.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class PasswordGenerator {

    private final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private final String NUMBERS = "0123456789";
    private final String SPECIAL = "!@#$%^&*()-_=+[]{}<>?/";

    private final String ALL = UPPER + LOWER + NUMBERS + SPECIAL;
    private final SecureRandom random = new SecureRandom();

    public String generate() {
        int length = 10;
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(ALL.length());
            password.append(ALL.charAt(index));
        }

        return password.toString();
    }

}
