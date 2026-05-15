import org.example.service.Validation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ValidationTest {

    @Test
    @DisplayName("isValidISBN: valid 13-digit ISBN")
    void isValidISBN_valid() {
        String input = "9780134685991";
        boolean expResult = true;
        boolean result = Validation.isValidISBN(input);
        assertEquals(expResult, result);
    }

    @Test
    @DisplayName("isValidISBN: too short")
    void isValidISBN_tooShort() {
        String input = "12345";
        boolean expResult = false;
        boolean result = Validation.isValidISBN(input);
        assertEquals(expResult, result);
    }

    @Test
    @DisplayName("isValidISBN: contains letters")
    void isValidISBN_letters() {
        String input = "978013468599A";
        boolean expResult = false;
        boolean result = Validation.isValidISBN(input);
        assertEquals(expResult, result);
    }

    @Test
    @DisplayName("isValidId: valid ID with letter prefix")
    void isValidId_valid() {
        String input = "B0001";
        boolean expResult = true;
        boolean result = Validation.isValidId(input);
        assertEquals(expResult, result);
    }

    @Test
    @DisplayName("isValidId: missing prefix")
    void isValidId_noPrefix() {
        String input = "10001";
        boolean expResult = false;
        boolean result = Validation.isValidId(input);
        assertEquals(expResult, result);
    }

    @Test
    @DisplayName("isValidId: too long")
    void isValidId_tooLong() {
        String input = "B00001";
        boolean expResult = false;
        boolean result = Validation.isValidId(input);
        assertEquals(expResult, result);
    }

    @Test
    @DisplayName("isValidName: normal name")
    void isValidName_normal() {
        String input = "Alice";
        boolean expResult = true;
        boolean result = Validation.isValidName(input);
        assertEquals(expResult, result);
    }

    @Test
    @DisplayName("isValidName: empty string")
    void isValidName_empty() {
        String input = "";
        boolean expResult = false;
        boolean result = Validation.isValidName(input);
        assertEquals(expResult, result);
    }

    @Test
    @DisplayName("isValidName: contains number")
    void isValidName_number() {
        String input = "Al1ce";
        boolean expResult = false;
        boolean result = Validation.isValidName(input);
        assertEquals(expResult, result);
    }
}