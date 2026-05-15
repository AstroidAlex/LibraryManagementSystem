package org.example.domain;

import lombok.Getter;
import lombok.Setter;
import org.example.interfaces.Reportable;
import org.example.service.Util;
import org.example.service.Validation;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class Library implements Reportable {
    private final List<Item> items = new LinkedList<>();
    private final Map<String, User> users = new HashMap<>();

    /**
     * Is where the user adds the item to the list of items
     * @param item is the item to be added
     */
    public void addItem(Item item) {
        items.add(item);
        backupData();
    }

    /**
     * Is where the user is added to the list of users
     * @param user is the user to be added
     */
    public void addUser(User user) {
        users.put(user.getId().toUpperCase(), user);
        backupData();
    }

    /**
     * allows the removal of the users and backsup the change
     * @param userId is the id of the user desired to be removed
     */
    public void removeUser(String userId) {
        if (users.containsKey(userId.toUpperCase())) {
            users.remove(userId);
            System.out.println("User successfully removed");
        } else {
            System.out.println("User was not removed successfully");
        }
        backupData();
    }

    /**
     * Allows the user to borrow an item after having gone through numerous exception checks
     * @param userId is the id of the user who desires to borrow something
     * @param itemId is the id of the item desired to be borrowed.
     *               On the chance that the item id is taken, it checks if there is another identical book
     *               but with a different title that is available.
     */
    public void borrowItem(String userId, String itemId) {
        User user = users.get(userId.toUpperCase());
        if (!(Validation.isValidId(itemId) || Validation.isValidId(userId))) {
            throw new IllegalArgumentException("Id is not valid");
        }
        Item item = null; //temp
        for (Item it : items) {
            System.out.println(it);//bypasses the need to set items to map. Other methods need items as a list
            if (it.getId().equalsIgnoreCase(itemId)) {
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
                    ("Sorry, %s is unavailable\n", itemId));
        }
        if (!user.canBorrow(item)) { //checks if user can borrow item
            throw new IllegalArgumentException(
                    String.format("%s cannot borrow %s type items\n", user.getClass().getSimpleName(), type));
        }
        if (user.borrowedItems.size() > user.getBorrowingLimit()) { //checks if it exceeds borrowing limit
            throw new IllegalArgumentException(
                    String.format("%s cannot borrow more than %d items\n",
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
        System.out.printf("%s has successfully borrowed %s,%s\n", userId, item.getTitle(), item.getId());
    }

    /**
     * Allows the user to return the item after confirming they have the item
     * @param userId is the user id of the person returning the item
     * @param itemId is the id of the item being returned
     */
    public void returnItem(String userId, String itemId) {
        User user = users.get(userId.toUpperCase(Locale.ROOT));
        System.out.println(user);
        Item item = null; //temp
        for (Item it : items) { //bypasses the need to set items to map. Other methods need items as a list
            if (it.getId().equalsIgnoreCase(itemId)) {
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
        backupData();
        item.returnItem();
        user.returnItem(item);
        System.out.printf("%s has been successfully returned", item.getTitle());
    }

    /**
     * does what I hope is a recursive search by going through slowly every option until we get to the desired title
     * @param title is the title being searched for
     * @return the results found. If nothing comes out, there were no results.
     */
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

    /**
     * Allows the user to find everything by the title using stream method
     * @param title is the title of the item searched for
     * @return the books found (no duplicates thanks to map)
     */
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

    /**
     * allows the user to search by author using the stream method to search
     * @param author the desired author to be found
     * @return the results that the author requested made
     */
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

    /**
     * sorts items by a choosen comparator
     * @param comparator is the way the person intends the items to be compared based on the choosen comparator
     * @return the items sorted by method desired
     */
    public List<Item> itemsSorted(Comparator<Item> comparator) {
        items.sort(comparator);
        return items;
    }

    /**
     * sorts the users by method desired
     * @param comparator is the comparator choosen
     * @return the users now sorted by desired way
     */
    public List<User> usersSorted(Comparator<User> comparator) {
        List<User> sorted = new ArrayList<>(users.values());
        sorted.sort(comparator);
        return sorted;
    }

    /**
     * is called whenever things have been changed and should be done in the csv files
     */
    public void backupData(){
        Util.rewriteItemsFile(items);
        Util.rewriteUserFile(new ArrayList<>(users.values()));
    }

    /**
     * gives the ability to load information but not very practical
     */
    public void loadData() {
        Util.loadItems();
        Util.loadUsers();
    }

    /**
     * is the entire generateReport() that gives details about all requested elements, no history though
     * @return the status of items and how many users/items there are
     */
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

    /**
     * it is a required method that helps the generate report count how many of each item it is to help keep
     * everything clean and easy to debug whenever changes occur
     * @param status allows the method above to count easier the items based on status
     * @return the count of each item by status
     */
    private long countByStatus(Item.ItemStatus status) {
        return items.stream()
                .filter(item -> item.getStatus() == status)
                .count();
    }
}
