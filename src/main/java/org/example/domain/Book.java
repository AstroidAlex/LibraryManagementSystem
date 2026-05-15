package org.example.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.example.service.Validation;

@EqualsAndHashCode(callSuper = false)
@Getter
@ToString
public class Book extends Item{
    private final String isbn;
    private final String genre;
    private final String author;

    public Book(String title, ItemStatus status, String isbn, String creator, String genre) {
        super(title, status, creator);
        this.isbn = Validation.isValidISBN(isbn) ? isbn : null; //checks if a valid isbn
        this.genre = genre;
        this.author = creator;
        setId(String.format("B%4s", getId()));
        setType(Type.BOOK);
    }

}
