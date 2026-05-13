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
        User user = users.get(userId);
        Item item = items.get(itemId);
        String type = item instanceof Book ? "Book" : item instanceof DVD ? "DVD" : "Magazine";
        if (!user.canBorrow(item)) {
            throw new IllegalArgumentException(
                    String.format("%s cannot borrow %s items", user.getClass().getSimpleName(), type));
        }
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
        return results; //TODO: NO DUPLICATES
    }

    public List<Item> findAllByTitleStream(String title) {
        if (title == null) return Collections.emptyList();
        String target = title.trim().toLowerCase();
        List<Item> items = getItems();
        if (items == null) return Collections.emptyList();
        Map<Object, Item> map = items.stream().filter(Objects::nonNull)
                .filter(item -> {
                    String itemTitle = item.getTitle();
                    return itemTitle != null && itemTitle.trim().toLowerCase().equals(target);})
                .collect(Collectors.toMap(
                        Item::getId, item -> item, (existing, replacement) //although unused,
                                // map doesn't work without it
                                -> existing, LinkedHashMap::new));

        return new ArrayList<>(map.values());
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

    public List<Book> searchByAuthorStream(String author) {
        String lower = author.toLowerCase();
        Map<String, Book> map = new LinkedHashMap<>();
        items.stream()
                .filter(item -> item instanceof Book)
                .map(item -> (Book) item)
                .filter(book -> book.getAuthor() != null && book.getAuthor().toLowerCase().contains(lower))
                .forEach(book -> map.putIfAbsent(book.getId(), book)); //no duplicates :)
        return new ArrayList<>(map.values());
    }

}
