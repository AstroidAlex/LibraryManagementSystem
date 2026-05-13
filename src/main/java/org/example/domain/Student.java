package org.example.domain;

import java.util.List;

public class Student extends User{
    public Student(List<Item> borrowedItems, String name) {
        super(borrowedItems, name);
        setId(String.format("S%4s", getId()));
    }

    @Override
    public int getBorrowingLimit() {
        return 5;
    }
    @Override
    public boolean canBorrow(Item item) {
        return item instanceof Book;
    }
}
