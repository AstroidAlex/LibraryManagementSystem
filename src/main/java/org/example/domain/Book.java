package org.example.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class Book extends Item{
    private final String isbn;
    private final String author;
    private final String genre;

    public Book(String title, ItemStatus status, String isbn, String author, String genre) {
        super(title, status);
        this.isbn = isbn; //TODO: invalid isbn validation
        this.author = author;
        this.genre = genre;
    }
}
