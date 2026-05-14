package org.example.domain;

import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class Library {
    private final List<Item> items = new LinkedList<>();
    private final Map<String, User> users = new HashMap<>();


    public void addItem(Item item) {
        items.add(item);

    }

    public void addUser(User user) {
        users.put(user.getId(), user);
    }

    public boolean removeUser(String userId) {
        if (users.containsKey(userId)) {
            users.remove(userId);
            return true;
        }
        return false;
    }

    public void borrowItem(String userId, String itemId) {
        User user = users.get(userId);
        Item item = null; //temp
        for (Item it : items) { //bypasses the need to set items to map. Other methods need items as a list
            if (it.getId().equals(itemId)) {
                item = it; //TODO: check if this makes it more than just the id
                break;
            }
        }
        String type = item instanceof Book ? "Book" : item instanceof DVD ? "DVD" : "Magazine";
        if (!user.canBorrow(item)) {
            throw new IllegalArgumentException(
                    String.format("%s cannot borrow %s items", user.getClass().getSimpleName(), type));
        }
        if (user.borrowedItems.size() > user.getBorrowingLimit()) {
            throw new IllegalArgumentException(
                    String.format("%s cannot borrow more than %d items",
                            user.getClass().getSimpleName(), user.getBorrowingLimit()));
        }
        if (user.hasBorrowed(item)) { //no duplicate borrowing
            throw new IllegalArgumentException("Can not borrow more than 1 copy of the same item");
        }
        if (item != null) {
            item.borrow();
            user.borrowedItems.add(item);
            user.borrow(item);
        }
    }

    public void returnItem(String userId, String itemId) {
        User user = users.get(userId);
        Item item = null; //temp
        for (Item it : items) { //bypasses the need to set items to map. Other methods need items as a list
            if (it.getId().equals(itemId)) {
                item = it;
                break;
            }
        }
        if (item != null) {
            item.returnItem();
            user.returnItem(item);
        }
    }

    public List<Item> searchByTitleRecursive(String title) {
        List<Item> results = new ArrayList<>();
        if (title == null) return results;
        String target = title.trim().toLowerCase();
        List<Item> items = getItems();
        if (items == null) return results;
        for (Item item : items) {
            if (item == null || item.getTitle() == null) continue;
            if (item.getTitle().trim().toLowerCase().equals(target)) results.add(item);
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

    public List<Item> itemsSorted(Comparator<Item> comparator) {
        items.sort(comparator);
        return items;
    }

    public List<User> usersSorted(Comparator<User> comparator) {
        List<User> sorted = new ArrayList<>(users.values());
        sorted.sort(comparator);
        return sorted;
    }


    public String generateReport(User user) {
        if (!(user instanceof Admin)) {
            return null;
        }
        long available = countByStatus(Item.ItemStatus.AVAILABLE);
        long borrowed = countByStatus(Item.ItemStatus.BORROWED);
        long lost = countByStatus(Item.ItemStatus.LOST);

         return String.format("Total Items:    %d%n", items.size()) +
                String.format("Available:      %d%n", available) +
                String.format("Borrowed:       %d%n", borrowed) +
                String.format("Lost:           %d%n", lost) +
                String.format("Total Users:    %d%n", users.size());

    }

    private long countByStatus(Item.ItemStatus status) {
        return items.stream()
                .filter(item -> item.getStatus() == status)
                .count();
    }
}
