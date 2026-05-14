package org.example.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode(callSuper = false)
@Getter
@ToString
public class Book extends Item{
    private final String isbn;
    private final String genre;
    private final String author;

    public Book(String title, ItemStatus status, String isbn, String creator, String genre) {
        super(title, status, creator);
        this.isbn = isbn; //TODO: invalid isbn validation
        this.genre = genre;
        this.author = creator;
        setId(String.format("B%4s", getId()));
        setType(Type.BOOK);
    }
}
