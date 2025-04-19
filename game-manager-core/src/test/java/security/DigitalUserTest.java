package security;

import com.tracktainment.gamemanager.security.context.DigitalUser;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DigitalUserTest {

    @Test
    void shouldCreateDigitalUserUsingBuilder() {
        // Arrange
        String id = UUID.randomUUID().toString();
        String subject = "auth2|123456789";

        // Act
        DigitalUser digitalUser = DigitalUser.builder()
                .id(id)
                .subject(subject)
                .build();

        // Assert
        assertEquals(id, digitalUser.getId());
        assertEquals(subject, digitalUser.getSubject());
    }

    @Test
    void shouldCreateEmptyDigitalUserUsingNoArgsConstructor() {
        // Act
        DigitalUser digitalUser = new DigitalUser();

        // Assert
        assertNull(digitalUser.getId());
        assertNull(digitalUser.getSubject());
    }

    @Test
    void shouldCreateDigitalUserUsingAllArgsConstructor() {
        // Arrange
        String id = UUID.randomUUID().toString();
        String subject = "auth2|123456789";

        // Act
        DigitalUser digitalUser = new DigitalUser(id, subject);

        // Assert
        assertEquals(id, digitalUser.getId());
        assertEquals(subject, digitalUser.getSubject());
    }

    @Test
    void shouldUseSettersAndGetters() {
        // Arrange
        DigitalUser digitalUser = new DigitalUser();
        String id = UUID.randomUUID().toString();
        String subject = "auth2|123456789";

        // Act
        digitalUser.setId(id);
        digitalUser.setSubject(subject);

        // Assert
        assertEquals(id, digitalUser.getId());
        assertEquals(subject, digitalUser.getSubject());
    }

    @Test
    void shouldImplementEqualsAndHashCode() {
        // Arrange
        String id1 = UUID.randomUUID().toString();
        String id2 = UUID.randomUUID().toString();
        String subject = "auth2|123456789";

        DigitalUser user1 = DigitalUser.builder()
                .id(id1)
                .subject(subject)
                .build();

        DigitalUser user2 = DigitalUser.builder()
                .id(id1)  // Same ID
                .subject(subject)
                .build();

        DigitalUser user3 = DigitalUser.builder()
                .id(id2)  // Different ID
                .subject(subject)
                .build();

        // Assert
        assertEquals(user1, user1); // Same object reference
        assertEquals(user1, user2); // Equal by value
        assertNotEquals(user1, user3); // Different values
        assertEquals(user1.hashCode(), user2.hashCode()); // Same hash code for equal objects
        assertNotEquals(user1.hashCode(), user3.hashCode()); // Different hash code for different objects
        assertNotEquals(user1, null); // Not equal to null
        assertNotEquals(user1, "not a DigitalUser"); // Not equal to different type
    }

    @Test
    void shouldImplementToString() {
        // Arrange
        String id = UUID.randomUUID().toString();
        String subject = "auth2|123456789";

        DigitalUser digitalUser = DigitalUser.builder()
                .id(id)
                .subject(subject)
                .build();

        // Act
        String toString = digitalUser.toString();

        // Assert
        assertNotNull(toString);
        assertFalse(toString.isEmpty());
        assertTrue(toString.contains("DigitalUser"));
        assertTrue(toString.contains(id));
        assertTrue(toString.contains(subject));
    }
}
