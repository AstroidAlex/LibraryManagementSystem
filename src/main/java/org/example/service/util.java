package org.example.service;

import org.example.domain.Book;
import org.example.domain.DVD;
import org.example.domain.Item;
import org.example.domain.Magazine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class util {
    void registerNewItem(Item item) {
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
}
