package org.example.domain;

import java.util.List;

public class Admin extends User{
    private final Library library;

    public Admin(List<Item> borrowedItems, String name, Library library) {
        super(borrowedItems, name);
        this.library = library;
    }

    @Override
    public int getBorrowingLimit() {
        return Integer.MAX_VALUE; //no limit essentially
    }

    @Override
    public boolean canBorrow(Item item) {
        return true; //can borrow all
    }

    //TODO: generate a report
}
