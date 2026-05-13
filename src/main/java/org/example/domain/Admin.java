package org.example.domain;

import java.util.List;

public class Admin extends User{

    public Admin(List<Item> borrowedItems, String name) {
        super(borrowedItems, name);
        setId(String.format("A%4s", getId()));
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
