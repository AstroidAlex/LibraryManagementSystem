package org.example.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class Item {
    protected final String id;
    protected String title;
    protected ItemStatus status;
    protected String author;

    private static int nextId = 1;

    public Item(String title, ItemStatus status, String author) {
        this.id = String.format("%04d", nextId++); //TODO: adapt for different types
        this.title = title;
        this.status = status;
        this.author = author;
    }

    public void returnItem() {
        status = ItemStatus.AVAILABLE;
    }
    public void markLost() {
        status = ItemStatus.LOST;
    }

    /**
     * can be used when adding new items
     */
    public boolean isAvailable() {
        return status == ItemStatus.AVAILABLE;
    }

    public enum ItemStatus {
        AVAILABLE, BORROWED, LOST
    }
}
