package org.example.service;

public class Validation {
    public boolean isValidISBN(String isbn) {
        return isbn.matches("\\d{13}");
    }
}
