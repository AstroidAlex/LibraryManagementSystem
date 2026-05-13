package org.example.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class Magazine extends Item{
    private final String issueNumber;
    private final String publisher;

    public Magazine(String title, ItemStatus status, String author, String issueNumber, String publisher) {
        super(title, status, author);
        this.issueNumber = issueNumber;
        this.publisher = publisher;
    }
}
