import org.example.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

    private Library library;
    private Student student;
    private Teacher teacher;
    private Book book;
    private DVD dvd;

    @BeforeEach //kindly suggested by a reddit thread and works great
    void setUp() {
        library = new Library();
        student = new Student(new ArrayList<>(), "Alice");
        teacher = new Teacher(new ArrayList<>(), "Bob");
        book = new Book("Java 101", Item.ItemStatus.AVAILABLE, "9780000000001", "Author A", "Programming");
        dvd = new DVD("Inception", Item.ItemStatus.AVAILABLE, "Nolan", 148);

        library.addUser(student);
        library.addUser(teacher);
        library.addItem(book);
        library.addItem(dvd);
    }

    @Test
    @DisplayName("borrowItem: student borrows book")
    void borrowItem_studentBook() {
        String userId = student.getId();
        String itemId = book.getId();

        library.borrowItem(userId, itemId);

        boolean expResult = true;
        boolean result = student.hasBorrowed(book);
        assertEquals(expResult, result);
    }

    @Test
    @DisplayName("borrowItem: student cannot borrow DVD")
    void borrowItem_studentDVD() {
        String userId = student.getId();
        String itemId = dvd.getId();

        assertThrows(IllegalArgumentException.class, () -> {
            library.borrowItem(userId, itemId);
        });
    }

    @Test
    @DisplayName("borrowItem: exceeds limit")
    void borrowItem_exceedsLimit() {
        for (int i = 0; i < 5; i++) {
            Book b = new Book("Book" + i, Item.ItemStatus.AVAILABLE, "978000000100" + i, "Author", "Genre");
            library.addItem(b);
            library.borrowItem(student.getId(), b.getId());
        }
        Book extra = new Book("Extra", Item.ItemStatus.AVAILABLE, "9780000001999", "Author", "Genre");
        library.addItem(extra);

        String userId = student.getId();
        String itemId = extra.getId();

        assertThrows(IllegalArgumentException.class, () -> {
            library.borrowItem(userId, itemId);
        });
    }

    @Test
    @DisplayName("returnItem: returns borrowed book")
    void returnItem_success() {
        String userId = student.getId();
        String itemId = book.getId();
        library.borrowItem(userId, itemId);

        library.returnItem(userId, itemId);

        boolean expResult = false;
        boolean result = student.hasBorrowed(book);
        assertEquals(expResult, result);
    }

    @Test
    @DisplayName("returnItem: user doesn't have item")
    void returnItem_notOwned() {
        String userId = student.getId();
        String itemId = book.getId();

        assertThrows(IllegalArgumentException.class, () -> {
            library.returnItem(userId, itemId);
        });
    }

    @Test
    @DisplayName("returnItem: item not found")
    void returnItem_notFound() {
        String userId = student.getId();
        String itemId = "Z9999";

        assertThrows(IllegalArgumentException.class, () -> {
            library.returnItem(userId, itemId);
        });
    }

    @Test
    @DisplayName("searchByTitleRecursive: finds book")
    void searchByTitleRecursive_found() {
        String input = "Java 101";
        int expResult = 1;
        int result = library.searchByTitleRecursive(input).size();
        assertEquals(expResult, result);
    }

    @Test
    @DisplayName("searchByTitleRecursive: not found")
    void searchByTitleRecursive_notFound() {
        String input = "DoesNotExist";
        int expResult = 0;
        int result = library.searchByTitleRecursive(input).size();
        assertEquals(expResult, result);
    }

    @Test
    @DisplayName("searchByTitleRecursive: null input")
    void searchByTitleRecursive_null() {
        String input = null;
        int expResult = 0;
        int result = library.searchByTitleRecursive(input).size();
        assertEquals(expResult, result);
    }

    @Test
    @DisplayName("findAllByTitleStream: finds book")
    void findAllByTitleStream_found() {
        String input = "Java 101";
        int expResult = 1;
        int result = library.findAllByTitleStream(input).size();
        assertEquals(expResult, result);
    }

    @Test
    @DisplayName("findAllByTitleStream: not found")
    void findAllByTitleStream_notFound() {
        String input = "DoesNotExist";
        int expResult = 0;
        int result = library.findAllByTitleStream(input).size();
        assertEquals(expResult, result);
    }

    @Test
    @DisplayName("findAllByTitleStream: null input")
    void findAllByTitleStream_null() {
        String input = null;
        int expResult = 0;
        int result = library.findAllByTitleStream(input).size();
        assertEquals(expResult, result);
    }

    @Test
    @DisplayName("generateReport: contains totals")
    void generateReport_totals() {
        String result = library.generateReport();

        assertTrue(result.contains("Total Items"));
        assertTrue(result.contains("Available"));
    }

    @Test
    @DisplayName("generateReport: correct item count")
    void generateReport_itemCount() {
        String result = library.generateReport();

        boolean expResult = true;
        boolean containsTwo = result.contains("Total Items:    2");
        assertEquals(expResult, containsTwo);
    }

    @Test
    @DisplayName("generateReport: correct user count")
    void generateReport_userCount() {
        String result = library.generateReport();

        boolean expResult = true;
        boolean containsTwo = result.contains("Total Users:    2");
        assertEquals(expResult, containsTwo);
    }
}