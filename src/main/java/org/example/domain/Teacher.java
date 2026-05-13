package org.example.domain;

import java.util.List;

public class Teacher extends User{
    public Teacher(List<Item> borrowedItems, String name) {
        super(borrowedItems, name);
        setId(String.format("T%4s", getId()));
    }

    @Override
    public int getBorrowingLimit() {
        return 10;
    }

    @Override
    public boolean canBorrow(Item item) {
        return true; //can borrow all
    }
}
