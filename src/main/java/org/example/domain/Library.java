package org.example.domain;

import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class Library {
    private final List<Item> items = new LinkedList<>();
    private final Map<String, User> users = new HashMap<>();


    private final Queue<String> processingQueue = new LinkedList<>();
    private final Set<String> genres = new HashSet<>();

    public void addItem(Item item) {
        items.add(item);
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

    public List<Item> searchByTitleRecursive(String title) {
        List<Item> results = new ArrayList<>();
        if (title == null) return results;
        String target = title.trim().toLowerCase();
        List<Item> items = getItems();
        if (items == null) return results;
        for (Item it : items) {
            if (it == null || it.getTitle() == null) continue;
            if (it.getTitle().trim().toLowerCase().equals(target)) results.add(it);
        }
        return results;
    }

    public List<Item> findAllByTitleStream(String title) {
        if (title == null) return Collections.emptyList();
        String target = title.trim().toLowerCase();
        List<Item> items = getItems();
        if (items == null) return Collections.emptyList();
        return items.stream()
                .filter(Objects::nonNull)
                .filter(i -> i.getTitle() != null)
                .filter(i -> i.getTitle().trim().toLowerCase().equals(target))
                .collect(Collectors.toList());
    }
    public List<Item> findAllByAuthor(String author) {
        if (author == null) return Collections.emptyList();
        String a = author.trim().toLowerCase(); //all lowercase
        List<Item> items = getItems();
        if (items == null) return Collections.emptyList();
        Map<Object, Item> map = items.stream()
                .filter(Objects::nonNull)
                .filter(item -> {
                    String itemAuthor = item.getAuthor();
                    return itemAuthor != null && itemAuthor.trim().toLowerCase().equals(a);})
                .collect(Collectors.toMap(
                        Item::getId, item -> item, (existing, replacement) -> existing,
                        LinkedHashMap::new));
        return new ArrayList<>(map.values());
    }
}
