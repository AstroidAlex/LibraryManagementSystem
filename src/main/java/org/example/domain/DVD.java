package org.example.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class DVD extends Item{
    private final String director;
    private final int duration;

    public DVD(String title, ItemStatus status, String creator, int duration) {
        super(title, status, creator);
        this.director = creator;
        this.duration = duration;
        setId(String.format("D%4s", getId()));
    }
}
