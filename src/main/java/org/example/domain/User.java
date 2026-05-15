package org.example.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.service.Util;

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
        item.setStatus(Item.ItemStatus.BORROWED);
        Util.registerItemStatus(item.getId(), item.getStatus());
    }

    /**
     * Allows the user to give back the item
     * @param item is the item that wants to be returned
     */
    public void returnItem(Item item) {
        if (!borrowedItems.contains(item)) {
            throw new IllegalArgumentException("The user does not have this item");
        }
        borrowedItems.remove(item);
        item.setStatus(Item.ItemStatus.AVAILABLE);
    }

    /**
     * Checks if the person already has the item
     * @param item is the item that you want to check if the user has
     * @return whether or not that the user has the item
     */
    public boolean hasBorrowed(Item item) {
        return borrowedItems.contains(item);
    }


}
