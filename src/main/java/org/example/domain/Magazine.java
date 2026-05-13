package org.example.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class Magazine extends Item{
    private final String issueNumber;
    private final String publisher;

    public Magazine(String title, ItemStatus status, String creator, String issueNumber) {
        super(title, status, creator);
        this.issueNumber = issueNumber;
        this.publisher = creator;
        setId(String.format("M%4s", getId()));
    }
}
