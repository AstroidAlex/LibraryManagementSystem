package org.example.domain;

import java.util.List;

public class Student extends User{
    public Student(List<Item> borrowedItems, String name) {
        super(borrowedItems, name);
        setId(String.format("S%4s", getId()));
    }

    /**
     * it gets this user's specific borrow limit
     * @return the limit of items the students can borrow of 5
     */
    @Override
    public int getBorrowingLimit() {
        return 5;
    }

    /**
     * Lets the user only borrow books
     * @param item is the item you want to check if the user can borrow
     * @return whether or not the item can be borrowed by said user
     */
    @Override
    public boolean canBorrow(Item item) {
        return item instanceof Book;
    }
}
