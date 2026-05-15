package org.example.service;

import org.example.domain.User;

import java.util.List;

public class Validation {
    /**
     * checks if the isbn of the book is of valid length and only numbers
     * @param isbn is the isbn to be checked
     * @return whether or not the isbn matches the conditions
     */
    public static boolean isValidISBN(String isbn) {
        return isbn.matches("\\d{13}");
    }

    /**
     * checks if the id is valid based on each imposed condition such as the prefix exists and that the rest are numbers
     * @param id is the provided id to check
     * @return whether it is valid or not
     */
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

    /**
     * checks if the name is actually a name and not some random characters
     * @param name is the name to be checked
     * @return whether it is valid as a name or not
     */
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

    /**
     * confirms it is in fact not empty
     * @param str the str that will be checked if empty or not
     * @return if the str is not empty = true or false if its empty
     */
    public static boolean isNotEmpty(String str) {
        return str != null && !str.isEmpty();
    }
}
