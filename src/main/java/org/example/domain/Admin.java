package org.example.domain;

import org.example.interfaces.Reportable;

import java.util.List;

public class Admin extends User implements Reportable {
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

    @Override
    public String generateReport() {
        return String.format("Admin Report: ID=%s, Name=%s",getId(), getName()); //data is made in library
    }
}
