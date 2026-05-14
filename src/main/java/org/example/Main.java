package org.example;


import org.example.domain.*;
import org.example.service.Util;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        //Creating school...
        List<User> users = Util.loadUsers();
        List<Item> items = Util.loadItems();

    }
}