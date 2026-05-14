package org.example.service;

public class Validation {
    public static boolean isValidISBN(String isbn) {
        return isbn.matches("\\d{13}");
    }
    public static boolean isValidId(String id) {
        if (!Character.isAlphabetic(id.charAt(0))) {
            return false;
        }
        for (int i = 0; i < id.length() - 1; i++) {
            if (!Character.isDigit(id.charAt(i))) {
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
