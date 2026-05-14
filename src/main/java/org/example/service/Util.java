package org.example.service;

import org.example.domain.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Util {
    public static void registerNewItem(Item item) {
        File file = new File("src/main/resources/items.csv");
        try (FileWriter fw = new FileWriter(file)) {
            String field1 = switch (item) {
                case Book b-> b.getIsbn();
                case DVD d -> "";
                case Magazine m -> m.getIssueNumber();
                default -> throw new IllegalStateException("Unexpected value: " + item);
            };
            String field2 = switch (item) {
                case Book b-> b.getAuthor();
                case DVD d -> d.getDirector();
                case Magazine m -> m.getPublisher();
                default -> throw new IllegalStateException("Unexpected value: " + item);
            };
            String field3 = switch (item) {
                case Book b-> b.getGenre();
                case DVD d -> String.format("%d",d.getDuration());
                case Magazine m -> "";
                default -> throw new IllegalStateException("Unexpected value: " + item);
            };
            fw.write(String.format("%s,%s,%s,%s,%s,%s",item.getType(),item.getId(),item.getTitle(),
                    field1,field2,field3));
        } catch (IOException e) {
            System.out.println("Failed to write to the file");
        }
    }
    public static List<Item> loadItems() {
        String path = "src/main/resources/item.csv";
        File file = new File(path);

        List<Item> items = new ArrayList<>();

        try (Scanner input = new Scanner(file)) {
            input.nextLine();

            while (input.hasNext()) {
                String row = input.nextLine();
                String[] data = row.split(",");

                String type = data[0];
                String id = data[1];
                String title = toTitleCase(data[2]);
                Item item = getItem(data, type, title);
                if (item != null) {
                    item.setId(id);
                }
                items.add(item); // add the new student to the list
            }
        } catch (IOException e) {
            System.out.printf("File %s does not exist%n", path);
        }

        return items;
    }

    private static Item getItem(String[] data, String type, String title) {
        Item.ItemStatus status = switch (data[3]) {
            case "AVAILABLE" -> Item.ItemStatus.AVAILABLE;
            case "BORROWED" -> Item.ItemStatus.BORROWED;
            case "Lost" -> Item.ItemStatus.LOST;
            default -> null;
        };


        Item item = switch (type) {
            case "Book" -> new Book(title, status, data[4], data[5], data[6]);
            case "DVD" -> new DVD(title, status, data[5], Integer.parseInt(data[6]));
            case "Magazine" -> new Magazine(title, status, data[5]);
            default -> null;
        };
        return item;
    }
    public static void registerItemStatus(String itemId, Item.ItemStatus itemStatus) {
        List<Item> items = loadItems();
        for (Item item : items) {
            if (item.getId().equals(itemId)) {
                item.setStatus(itemStatus);
                break;
            }
        }
        rewriteItemsFile(items);
    }
    public static void rewriteItemsFile(List<Item> items) {
        File file = new File("src/main/resources/items.csv");

        try (FileWriter fw = new FileWriter(file, false)) { // false = overwrite, not append
            // fw.write("type,id,title,status,field1,field2,field3") example heading

            for (Item item : items) {
                // Build the CSV line based on item type
                String line = buildCSVLineItems(item);
                fw.write(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String buildCSVLineItems(Item item) {
        // Book,B0001,Effective Java,AVAILABLE,9780134685991,Joshua Bloch,Programming -> heading format example

        return switch (item.getType()) {
            case Item.Type.BOOK -> { Book book = (Book) item;
                yield String.format("Book,%s,%s,%s,%s,%s,%s", //found to use yield thanks to stackOverflow and Google
                    item.getId(), item.getTitle(), item.getStatus(),
                    book.getIsbn(), book.getAuthor(), book.getGenre());
            }

            case Item.Type.DVD -> { DVD dvd = (DVD) item;
                yield String.format("DVD,%s,%s,%s,%s,%s",
                    item.getId(), item.getTitle(), item.getStatus(),
                    dvd.getDirector(), dvd.getDuration());
            }

            case Item.Type.MAGAZINE -> { Magazine magazine = (Magazine) item;
                yield String.format("Magazine,%s,%s,%s,%s,%s",
                    item.getId(), item.getTitle(), item.getStatus(),
                    magazine.getIssueNumber(), magazine.getPublisher());
            }

            default -> " ";
        };
    }
    public static void rewriteUserFile(List<User> users) {
        File file = new File("src/main/resources/items.csv");

        try (FileWriter fw = new FileWriter(file, false)) { // false = overwrite, not append
            // fw.write("Type,ID,Name") format heading

            for (User user : users) {
                String line = buildCSVLineUser(user);
                fw.write(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static String buildCSVLineUser(User user) {
        if (user instanceof Student) {
            return String.format("Student,%s,%s",user.getId(),user.getName());
        } if (user instanceof Teacher) {
            return String.format("Teacher,%s,%s",user.getId(),user.getName());
        } if (user instanceof Admin) {
            return String.format("Admin,%s,%s",user.getId(),user.getName());
        }
        return " ";
    }

    public static void registerNewUser(User user) {
        File file = new File("src/main/resources/users.csv");
        try (FileWriter fw = new FileWriter(file)) {
            switch (user) {
                case Teacher teacher -> fw.write(String.format("Teacher,%s,%s", user.getId(), user.getName()));
                case Admin admin -> fw.write(String.format("Admin,%s,%s", user.getId(), user.getName()));
                case Student student -> fw.write(String.format("Student,%s,%s", user.getId(), user.getName()));
                case null, default -> throw new IllegalArgumentException("Not a valid user");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<User> loadUsers() {
        String path = "src/main/resources/users.csv";
        File file = new File(path);

        List<User> users = new ArrayList<>();

        try (Scanner input = new Scanner(file)) {
            input.nextLine();

            while (input.hasNext()) {
                String row = input.nextLine();
                String[] data = row.split(",");

                String type = data[0];
                String id = data[1];
                String name = toTitleCase(data[2]);

                User user = switch (type) {
                    case "Student" -> new Student(null, name);
                    case "Teacher" -> new Teacher(null, name);
                    case "Admin" -> new Admin(null, name);
                    default -> new Student(null, null);
                    //in case of no user this is my chosen default
                };
                user.setId(id);
                users.add(user); // add the new student to the list
            }
        } catch (IOException e) {
            System.out.printf("File %s does not exist%n", path);
        }

        return users;
    }

    static String toTitleCase(String str) {
        return str.substring(0,1).toUpperCase() +
                str.substring(1).toLowerCase();
    }
}
