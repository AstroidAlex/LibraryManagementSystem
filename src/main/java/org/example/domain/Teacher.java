package org.example.domain;

import java.util.List;

public class Teacher extends User{
    public Teacher(List<Item> borrowedItems, String name) {
        super(borrowedItems, name);
        setId(String.format("T%4s", getId()));
    }

    /**
     * borrowing limit of a teacher is always 10
     * @return 10
     */
    @Override
    public int getBorrowingLimit() {
        return 10;
    }

    /**
     * checks the teacher can borrow the item, yes it can
     * @param item is what wants to be borrowed
     * @return true, always assuming it exists
     */
    @Override
    public boolean canBorrow(Item item) {
        return true; //can borrow all
    }
}
