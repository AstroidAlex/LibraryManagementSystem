import org.example.service.Util;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    @Test
    @DisplayName("toTitleCase: lowercase name")
    void toTitleCase_lower() {
        String input = "alice";
        String expResult = "Alice";
        String result = Util.toTitleCase(input);
        assertEquals(expResult, result);
    }

    @Test
    @DisplayName("toTitleCase: all caps")
    void toTitleCase_caps() {
        String input = "ALICE";
        String expResult = "Alice";
        String result = Util.toTitleCase(input);
        assertEquals(expResult, result);
    }

    @Test
    @DisplayName("toTitleCase: two words")
    void toTitleCase_twoWords() {
        String input = "alice smith";
        String expResult = "Alice Smith";
        String result = Util.toTitleCase(input);
        assertEquals(expResult, result);
    }
}