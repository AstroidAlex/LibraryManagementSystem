package org.example.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class DVD extends Item{
    private final String director;
    private final int duration;

    public DVD(String title, ItemStatus status, String director, int duration) {
        super(title, status);
        this.director = director;
        this.duration = duration;
    }
}
