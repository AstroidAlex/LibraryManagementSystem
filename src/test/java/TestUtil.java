import org.example.service.Util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestUtil {
    @Test
    void toTitleCase() {
        String str = "johnathan m. apples";
        String expected = "Johnathan M. Apples";
        String actual = Util.toTitleCase(str);
        Assertions.assertEquals(expected, actual);
    }
}
