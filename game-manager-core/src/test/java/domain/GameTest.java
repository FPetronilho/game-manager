package domain;

import com.tracktainment.gamemanager.domain.Game;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void shouldCreateGameUsingBuilder() {
        // Arrange
        String id = UUID.randomUUID().toString();
        String title = "The Last of Us Part II";
        String platform = "PlayStation 5";
        String genre = "Action";
        String developer = "Naughty Dog";
        LocalDate releaseDate = LocalDate.of(2020, 6, 19);
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now().plusDays(1);

        // Act
        Game game = Game.builder()
                .id(id)
                .title(title)
                .platform(platform)
                .genre(genre)
                .developer(developer)
                .releaseDate(releaseDate)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();

        // Assert
        assertEquals(id, game.getId());
        assertEquals(title, game.getTitle());
        assertEquals(platform, game.getPlatform());
        assertEquals(genre, game.getGenre());
        assertEquals(developer, game.getDeveloper());
        assertEquals(releaseDate, game.getReleaseDate());
        assertEquals(createdAt, game.getCreatedAt());
        assertEquals(updatedAt, game.getUpdatedAt());
    }

    @Test
    void shouldCreateEmptyGameUsingNoArgsConstructor() {
        // Act
        Game game = new Game();

        // Assert
        assertNull(game.getId());
        assertNull(game.getTitle());
        assertNull(game.getPlatform());
        assertNull(game.getGenre());
        assertNull(game.getDeveloper());
        assertNull(game.getReleaseDate());
        assertNull(game.getCreatedAt());
        assertNull(game.getUpdatedAt());
    }

    @Test
    void shouldUseSettersAndGetters() {
        // Arrange
        Game game = new Game();
        String id = UUID.randomUUID().toString();
        String title = "The Last of Us Part II";
        String platform = "PlayStation 5";
        String genre = "Action";
        String developer = "Naughty Dog";
        LocalDate releaseDate = LocalDate.of(2020, 6, 19);
        LocalDateTime now = LocalDateTime.now();

        // Act
        game.setId(id);
        game.setTitle(title);
        game.setPlatform(platform);
        game.setGenre(genre);
        game.setDeveloper(developer);
        game.setReleaseDate(releaseDate);
        game.setCreatedAt(now);
        game.setUpdatedAt(now);

        // Assert
        assertEquals(id, game.getId());
        assertEquals(title, game.getTitle());
        assertEquals(platform, game.getPlatform());
        assertEquals(genre, game.getGenre());
        assertEquals(developer, game.getDeveloper());
        assertEquals(releaseDate, game.getReleaseDate());
        assertEquals(now, game.getCreatedAt());
        assertEquals(now, game.getUpdatedAt());
    }

    @Test
    void shouldImplementEqualsAndHashCode() {
        // Arrange
        String id1 = UUID.randomUUID().toString();
        String id2 = UUID.randomUUID().toString();

        Game game1 = Game.builder().id(id1).title("Game 1").build();
        Game game2 = Game.builder().id(id1).title("Game 1").build(); // Same ID
        Game game3 = Game.builder().id(id2).title("Game 1").build(); // Different ID

        // Assert
        assertEquals(game1, game2); // Same ID should be equal
        assertNotEquals(game1, game3); // Different ID should not be equal
        assertEquals(game1.hashCode(), game2.hashCode()); // Same ID should have same hashCode
        assertNotEquals(game1.hashCode(), game3.hashCode()); // Different ID should have different hashCode
    }

    @Test
    void shouldImplementToString() {
        // Arrange
        String id = UUID.randomUUID().toString();
        Game game = Game.builder()
                .id(id)
                .title("The Last of Us Part II")
                .build();

        // Act
        String toString = game.toString();

        // Assert
        assertNotNull(toString);
        assertFalse(toString.isEmpty());
        assertTrue(toString.contains("Game"));
        assertTrue(toString.contains(id));
        assertTrue(toString.contains("The Last of Us Part II"));
    }
}
