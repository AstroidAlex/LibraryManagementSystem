package org.example.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class Book extends Item{
    private final String isbn;
    private final String genre;

    public Book(String title, ItemStatus status, String isbn, String author, String genre) {
        super(title, status, author);
        this.isbn = isbn; //TODO: invalid isbn validation
        this.genre = genre;
        setId(String.format("B%4s", getId()));
    }
}
