package org.example.domain;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public abstract class Item {
    private Type type;
    protected String id;
    protected String title;
    protected ItemStatus status;
    protected String creator;

    private static int nextId = 1;

    public Item(String title, ItemStatus status, String creator) {
        this.id = String.format("%04d", nextId++);
        this.title = title;
        this.status = status;
        this.creator = creator;
    }

    /**
     * Sets the state of the item returned to Available.
     */
    public void returnItem() {
        status = ItemStatus.AVAILABLE;
    } //exceptions in library

    /**
     * allows to set the status of the item as lost
     */
    public void markLost() {
        status = ItemStatus.LOST;
    } //exceptions in library

    /**
     * can be used when adding new items
     */
    public boolean isAvailable() {
        return status == ItemStatus.AVAILABLE;
    }

    /**
     * Allows the user to borrow books
     */
    public void borrow() {
        if (status != ItemStatus.AVAILABLE) {
            throw new IllegalStateException("Item is not available. Status: " + status);
        }
        status = ItemStatus.BORROWED;
    }

    public enum Type {
        BOOK, DVD, MAGAZINE
    }

    public enum ItemStatus {
        AVAILABLE, BORROWED, LOST
    }
}
