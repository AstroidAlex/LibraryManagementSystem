package org.example.domain;

import org.example.interfaces.Reportable;

import java.util.List;

public class Admin extends User implements Reportable {
    public Admin(List<Item> borrowedItems, String name) {
        super(borrowedItems, name);
        setId(String.format("A%4s", getId()));
    }

    /**
     * borrowing limit is not existant for the admin, so I put in the biggest number I could think of
     * @return max value possible for an integer, will hopefully never be reached, using long is just wasteful
     */
    @Override
    public int getBorrowingLimit() {
        return Integer.MAX_VALUE; //no limit essentially
    }

    /**
     * checks if the admin can borrow items, the answer is yes
     * @param item checks if the item is valid, yes, its always yes
     * @return true, no matter what
     */
    @Override
    public boolean canBorrow(Item item) {
        return true; //can borrow all
    }

    /**
     * generates a report of only the admin rest is in library
     * It's one of those things that whenever I remove it stops working but when it's there being not useful
     * nothing breaks. I gave up on finding the fix
     * @return the admins generated report
     */
    @Override
    public String generateReport() {
        return String.format("Admin Report: ID=%s, Name=%s",getId(), getName()); //data is made in library
    }
}
