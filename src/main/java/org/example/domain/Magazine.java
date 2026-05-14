package org.example.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode(callSuper = false)
@Getter
@ToString
public class Magazine extends Item{
    private final String issueNumber;
    private final String publisher;

    public static int nextIssue = 1;
    public Magazine(String title, ItemStatus status, String creator) {
        super(title, status, creator);
        this.issueNumber = String.format("%04d", nextIssue++);
        this.publisher = creator;
        setId(String.format("M%4s", getId()));
    }
}
