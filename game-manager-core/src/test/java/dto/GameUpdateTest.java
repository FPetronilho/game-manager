package dto;

import com.tracktainment.gamemanager.dto.GameUpdate;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class GameUpdateTest {

    @Test
    void shouldCreateGameUpdateUsingBuilder() {
        // Arrange
        String title = "The Last of Us Part II";
        String platform = "PlayStation 5";
        String genre = "Action";
        String developer = "Naughty Dog";
        LocalDate releaseDate = LocalDate.of(2020, 6, 19);

        // Act
        GameUpdate gameUpdate = GameUpdate.builder()
                .title(title)
                .platform(platform)
                .genre(genre)
                .developer(developer)
                .releaseDate(releaseDate)
                .build();

        // Assert
        assertEquals(title, gameUpdate.getTitle());
        assertEquals(platform, gameUpdate.getPlatform());
        assertEquals(genre, gameUpdate.getGenre());
        assertEquals(developer, gameUpdate.getDeveloper());
        assertEquals(releaseDate, gameUpdate.getReleaseDate());
    }

    @Test
    void shouldCreateEmptyGameUpdateUsingNoArgsConstructor() {
        // Act
        GameUpdate gameUpdate = new GameUpdate();

        // Assert
        assertNull(gameUpdate.getTitle());
        assertNull(gameUpdate.getPlatform());
        assertNull(gameUpdate.getGenre());
        assertNull(gameUpdate.getDeveloper());
        assertNull(gameUpdate.getReleaseDate());
    }

    @Test
    void shouldUseSettersAndGetters() {
        // Arrange
        GameUpdate gameUpdate = new GameUpdate();
        String title = "The Last of Us Part II";
        String platform = "PlayStation 5";
        String genre = "Action";
        String developer = "Naughty Dog";
        LocalDate releaseDate = LocalDate.of(2020, 6, 19);

        // Act
        gameUpdate.setTitle(title);
        gameUpdate.setPlatform(platform);
        gameUpdate.setGenre(genre);
        gameUpdate.setDeveloper(developer);
        gameUpdate.setReleaseDate(releaseDate);

        // Assert
        assertEquals(title, gameUpdate.getTitle());
        assertEquals(platform, gameUpdate.getPlatform());
        assertEquals(genre, gameUpdate.getGenre());
        assertEquals(developer, gameUpdate.getDeveloper());
        assertEquals(releaseDate, gameUpdate.getReleaseDate());
    }

    @Test
    void shouldImplementToString() {
        // Arrange
        GameUpdate gameUpdate = GameUpdate.builder()
                .title("The Last of Us Part II: Remastered")
                .platform("PlayStation 5")
                .build();

        // Act
        String toString = gameUpdate.toString();

        // Assert
        assertNotNull(toString);
        assertFalse(toString.isEmpty());
        assertTrue(toString.contains("GameUpdate"));
        assertTrue(toString.contains("The Last of Us Part II: Remastered"));
        assertTrue(toString.contains("PlayStation 5"));
    }
}
