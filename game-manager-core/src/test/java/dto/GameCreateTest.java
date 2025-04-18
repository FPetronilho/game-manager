package dto;

import com.tracktainment.gamemanager.dto.GameCreate;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class GameCreateTest {

    @Test
    void shouldCreateGameCreateUsingBuilder() {
        // Arrange
        String title = "The Last of Us Part II";
        String platform = "PlayStation 5";
        String genre = "Action";
        String developer = "Naughty Dog";
        LocalDate releaseDate = LocalDate.of(2020, 6, 19);

        // Act
        GameCreate gameCreate = GameCreate.builder()
                .title(title)
                .platform(platform)
                .genre(genre)
                .developer(developer)
                .releaseDate(releaseDate)
                .build();

        // Assert
        assertEquals(title, gameCreate.getTitle());
        assertEquals(platform, gameCreate.getPlatform());
        assertEquals(genre, gameCreate.getGenre());
        assertEquals(developer, gameCreate.getDeveloper());
        assertEquals(releaseDate, gameCreate.getReleaseDate());
    }

    @Test
    void shouldCreateEmptyGameCreateUsingNoArgsConstructor() {
        // Act
        GameCreate gameCreate = new GameCreate();

        // Assert
        assertNull(gameCreate.getTitle());
        assertNull(gameCreate.getPlatform());
        assertNull(gameCreate.getGenre());
        assertNull(gameCreate.getDeveloper());
        assertNull(gameCreate.getReleaseDate());
    }

    @Test
    void shouldUseSettersAndGetters() {
        // Arrange
        GameCreate gameCreate = new GameCreate();
        String title = "The Last of Us Part II";
        String platform = "PlayStation 5";
        String genre = "Action";
        String developer = "Naughty Dog";
        LocalDate releaseDate = LocalDate.of(2020, 6, 19);

        // Act
        gameCreate.setTitle(title);
        gameCreate.setPlatform(platform);
        gameCreate.setGenre(genre);
        gameCreate.setDeveloper(developer);
        gameCreate.setReleaseDate(releaseDate);

        // Assert
        assertEquals(title, gameCreate.getTitle());
        assertEquals(platform, gameCreate.getPlatform());
        assertEquals(genre, gameCreate.getGenre());
        assertEquals(developer, gameCreate.getDeveloper());
        assertEquals(releaseDate, gameCreate.getReleaseDate());
    }

    @Test
    void shouldImplementToString() {
        // Arrange
        GameCreate gameCreate = GameCreate.builder()
                .title("The Last of Us Part II")
                .platform("PlayStation 5")
                .build();

        // Act
        String toString = gameCreate.toString();

        // Assert
        assertNotNull(toString);
        assertFalse(toString.isEmpty());
        assertTrue(toString.contains("GameCreate"));
        assertTrue(toString.contains("The Last of Us Part II"));
        assertTrue(toString.contains("PlayStation 5"));
    }
}
