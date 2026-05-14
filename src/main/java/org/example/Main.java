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
        System.out.println(library.generateReport());
        library.removeUser("S0001");
        library.borrowItem("s0001", "B0001");
    }
}