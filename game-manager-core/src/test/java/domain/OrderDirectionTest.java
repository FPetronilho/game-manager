package domain;

import com.tracktainment.gamemanager.domain.OrderDirection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderDirectionTest {

    @Test
    void shouldHaveCorrectValues() {
        // Assert
        assertEquals("ascending", OrderDirection.ASC.getValue());
        assertEquals("descending", OrderDirection.DESC.getValue());
        assertEquals(2, OrderDirection.values().length);
    }

    @Test
    void shouldImplementToString() {
        // Assert
        assertTrue(OrderDirection.ASC.toString().contains("ASC"));
        assertTrue(OrderDirection.DESC.toString().contains("DESC"));
    }
}
