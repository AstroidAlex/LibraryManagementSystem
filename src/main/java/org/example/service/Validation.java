package org.example.service;

import org.example.domain.User;

import java.util.List;

public class Validation {
    public static boolean isValidISBN(String isbn) {
        return isbn.matches("\\d{13}");
    }
    public static boolean isValidId(String id) {
        if (!isNotEmpty(id) || id.length() != 5) {
            System.out.println("invalid size");
            return false;
        }
        if (!Character.isAlphabetic(id.charAt(0))) {
            System.out.println("No letter Prefix");
            return false;
        }
        for (int i = 1; i < id.length(); i++) {
            if (!Character.isDigit(id.charAt(i))) {
                System.out.println("Not a digit");
                return false;
            }
        }
        return true;
    }

    public static boolean isValidName(String name) {
        if (!isNotEmpty(name)) {
            return false;
        }
        for (int i = 0; i < name.length() - 1; i++) {
            if (Character.isAlphabetic(name.charAt(i))) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotEmpty(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        return true;
    }
}
