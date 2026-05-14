package org.example.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@EqualsAndHashCode
@ToString
@Setter
public abstract class User {
    protected String id;
    protected String name;
    protected final List<Item> borrowedItems;

    private static int nextId = 1;
    public User(List<Item> borrowedItems, String name) {
        this.id = String.format("%04d", nextId++); //count is located here, prefix attributed in each class
        this.borrowedItems = borrowedItems;
        this.name = name;
    }

    public abstract int getBorrowingLimit();
    public abstract boolean canBorrow(Item item);

    public void borrow(Item item) {
        borrowedItems.add(item);
        registerBorrowedItem(item);
    }

    public void returnItem(Item item) {
        borrowedItems.remove(item);
    }

    public boolean hasBorrowed(Item item) {
        return borrowedItems.contains(item);
    }

    void registerBorrowedItem(Item item) {

    }
}
