package org.example.domain;

import lombok.Getter;

import java.util.*;

@Getter
public class Library {
    private Map<String, Item> items = new HashMap<>();
    private Map<String, User> users = new HashMap<>();

    private Queue<String> processingQueue = new LinkedList<>();
    private Set<String> genres = new HashSet<>();

    public void addItem(Item item) {
        items.put(item.getId(), item);
        if (item instanceof Book) {
            genres.add(((Book) item).getGenre());
        }
    }

    public void addUser(User user) {
        users.put(user.getId(), user);
    }

    public void borrowItem(String userId, String itemId) {
        //TODO: Update borrow and return once exceptions exist
    }
    public void returnItem(String userID, String itemId) {
        //update after
    }
}
