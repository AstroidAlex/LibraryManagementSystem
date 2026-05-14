package org.example;


import org.example.domain.*;
import org.example.service.Util;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        //Creating school...
        List<User> users = Util.loadUsers();
        List<Item> items = Util.loadItems();
        Library library = new Library();
        for (Item item : items) {
            library.addItem(item);
        }
        for (User user : users) {
            library.addUser(user);
        }
        library.borrowItem("s0002", "B0001");
        library.borrowItem("s0002", "B0002");

        library.returnItem("s0002", "b0001");
        library.returnItem("s0002", "b0002");
    }
}