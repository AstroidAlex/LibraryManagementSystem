package org.example.domain;

import lombok.Getter;
import org.example.interfaces.Reportable;
import org.example.service.Util;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class Library implements Reportable {
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
                item = it;
                break;
            }
        }

        if (item != null && !(item.getStatus() == Item.ItemStatus.AVAILABLE)) { //searches for alternative books
            String title = item.getTitle();
            System.out.println("Searching for alternatives with same title...");
            List<Item> alternatives = searchByTitleRecursive(title);
            for (Item alt : alternatives) {
                if (alt.getStatus() == Item.ItemStatus.AVAILABLE) {
                    System.out.println("Found available book");
                    item = alt;
                }
            }

        }
        String type = item instanceof Book ?
                "Book" : item instanceof DVD ? "DVD" : "Magazine"; // used to check if type of item is valid

        if (item == null) { //checks if results were found
            throw new IllegalArgumentException(String.format
                    ("Sorry, %s is unavailable", itemId));
        }
        if (!user.canBorrow(item)) { //checks if user can borrow item
            throw new IllegalArgumentException(
                    String.format("%s cannot borrow %s type items", user.getClass().getSimpleName(), type));
        }
        if (user.borrowedItems.size() > user.getBorrowingLimit()) { //checks if it exceeds borrowing limit
            throw new IllegalArgumentException(
                    String.format("%s cannot borrow more than %d items",
                            user.getClass().getSimpleName(), user.getBorrowingLimit()));
        }
        for (Item it1 : user.getBorrowedItems()) { //checks if the person is borrowing twice the same item
            if (it1.title.equals(item.getTitle())) {
                throw new IllegalArgumentException("User cannot borrow more than 1 copy of the same item");
            }
        }
        //does borrow process and confirms success
        item.borrow();
        user.borrowedItems.add(item);
        user.borrow(item);
        System.out.printf("%s has successfully borrowed %s,%s", userId, item.getTitle(), item.getId());
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
        if (item == null) {
            throw new IllegalArgumentException("Item was not found"); //added to avoid breaking due to using null
        }
        if (!user.hasBorrowed(item)) { //confirms user does have item to be returned
            throw new IllegalArgumentException("User doesn't own item");
        }
        //does the borrowing process plus confirmation of task completed
        item.returnItem();
        user.returnItem(item);
        System.out.println("Item has been successfully returned");
    }

    public List<Item> searchByTitleRecursive(String title) {
        List<Item> results = new ArrayList<>();
        if (title == null) return results;
        String target = title.trim().toLowerCase();
        List<Item> items = getItems();
        if (items == null) return results;
        for (Item item : items) {
            if (item == null || item.getTitle() == null || results.contains(item)) continue; //skips unwanted items
            if (item.getTitle().trim().toLowerCase().equals(target)) results.add(item);
        }
        return results;
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
    public void backupData(){
        Util.rewriteItemsFile(items);
        Util.rewriteUserFile((List<User>) users); //will see if it breaks
    }

    public String generateReport() {
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
