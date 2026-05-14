package org.example.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.service.Util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

    public void returnItem(Item item) {
        borrowedItems.remove(item);
        item.setStatus(Item.ItemStatus.AVAILABLE);
    }

    public boolean hasBorrowed(Item item) {
        return borrowedItems.contains(item);
    }


}
