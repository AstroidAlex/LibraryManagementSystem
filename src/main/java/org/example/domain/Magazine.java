package org.example.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class Magazine extends Item{
    private final String issueNumber;
    private final String publisher;

    public Magazine(String title, ItemStatus status, String issueNumber, String publisher) {
        super(title, status);
        this.issueNumber = issueNumber;
        this.publisher = publisher;
    }
}
