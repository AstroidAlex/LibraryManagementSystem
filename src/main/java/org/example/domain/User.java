package org.example.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@EqualsAndHashCode
@ToString
public abstract class User {
    protected String id;
    protected String name;
    protected final List<Item> borrowedItems;

    private static int nextId = 1;
    public User(List<Item> borrowedItems, String name) {
        this.id = String.format("%04d", nextId++); //TODO: adapt for different user types
        this.borrowedItems = borrowedItems;
        this.name = name;
    }

    public abstract int getBorrowingLimit();
    public abstract boolean canBorrow(Item item);

    public void borrowItem(Item item) {
        //TODO: soon
    }

    public void returnItem(Item item) {
        //TODO: soon
    }

    public boolean hasBorrowed(Item item) {
        return borrowedItems.contains(item);
    }

    void registerBorrowedItem(Item item) {
        //TODO: update after CSV
    }
}
