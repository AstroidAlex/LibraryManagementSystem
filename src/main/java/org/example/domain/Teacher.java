package org.example.domain;

import java.util.List;

public class Teacher extends User{
    public Teacher(List<Item> borrowedItems, String name) {
        super(borrowedItems, name);
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
