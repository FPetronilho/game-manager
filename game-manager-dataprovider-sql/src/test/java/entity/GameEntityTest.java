package entity;

import com.tracktainment.gamemanager.entity.BaseEntity;
import com.tracktainment.gamemanager.entity.GameEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GameEntityTest {

    @Test
    void shouldCreateGameEntityUsingBuilder() {
        // Arrange
        Long dbId = 1L;
        String id = UUID.randomUUID().toString();
        String title = "The Last of Us Part II";
        String platform = "PlayStation 5";
        String genre = "Action";
        String developer = "Naughty Dog";
        LocalDate releaseDate = LocalDate.of(2020, 6, 19);
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now().plusDays(1);

        // Act
        GameEntity gameEntity = GameEntity.builder()
                .dbId(dbId)
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
        assertEquals(dbId, gameEntity.getDbId());
        assertEquals(id, gameEntity.getId());
        assertEquals(title, gameEntity.getTitle());
        assertEquals(platform, gameEntity.getPlatform());
        assertEquals(genre, gameEntity.getGenre());
        assertEquals(developer, gameEntity.getDeveloper());
        assertEquals(releaseDate, gameEntity.getReleaseDate());
        assertEquals(createdAt, gameEntity.getCreatedAt());
        assertEquals(updatedAt, gameEntity.getUpdatedAt());
    }

    @Test
    void shouldCreateEmptyGameEntityUsingNoArgsConstructor() {
        // Act
        GameEntity gameEntity = new GameEntity();

        // Assert
        assertNull(gameEntity.getDbId());
        assertNull(gameEntity.getId());
        assertNull(gameEntity.getTitle());
        assertNull(gameEntity.getPlatform());
        assertNull(gameEntity.getGenre());
        assertNull(gameEntity.getDeveloper());
        assertNull(gameEntity.getReleaseDate());
        assertNull(gameEntity.getCreatedAt());
        assertNull(gameEntity.getUpdatedAt());
    }

    @Test
    void shouldUseSettersAndGetters() {
        // Arrange
        GameEntity gameEntity = new GameEntity();
        Long dbId = 2L;
        String id = UUID.randomUUID().toString();
        String title = "God of War";
        String platform = "PlayStation 5";
        String genre = "Action Adventure";
        String developer = "Santa Monica Studio";
        LocalDate releaseDate = LocalDate.of(2022, 11, 9);
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now().plusDays(1);

        // Act
        gameEntity.setDbId(dbId);
        gameEntity.setId(id);
        gameEntity.setTitle(title);
        gameEntity.setPlatform(platform);
        gameEntity.setGenre(genre);
        gameEntity.setDeveloper(developer);
        gameEntity.setReleaseDate(releaseDate);
        gameEntity.setCreatedAt(createdAt);
        gameEntity.setUpdatedAt(updatedAt);

        // Assert
        assertEquals(dbId, gameEntity.getDbId());
        assertEquals(id, gameEntity.getId());
        assertEquals(title, gameEntity.getTitle());
        assertEquals(platform, gameEntity.getPlatform());
        assertEquals(genre, gameEntity.getGenre());
        assertEquals(developer, gameEntity.getDeveloper());
        assertEquals(releaseDate, gameEntity.getReleaseDate());
        assertEquals(createdAt, gameEntity.getCreatedAt());
        assertEquals(updatedAt, gameEntity.getUpdatedAt());
    }

    @Test
    void shouldInheritFromBaseEntity() {
        // Arrange
        GameEntity gameEntity = new GameEntity();

        // Assert
        assertTrue(gameEntity instanceof BaseEntity);
    }

    @Test
    void shouldImplementEqualsAndHashCodeCorrectly() {
        // Arrange
        String id1 = UUID.randomUUID().toString();
        String id2 = UUID.randomUUID().toString();

        GameEntity entity1 = GameEntity.builder().id(id1).build();
        GameEntity entity2 = GameEntity.builder().id(id1).build(); // Same ID
        GameEntity entity3 = GameEntity.builder().id(id2).build(); // Different ID

        // Assert
        assertEquals(entity1, entity2); // Same ID should be equal
        assertNotEquals(entity1, entity3); // Different ID should not be equal
        assertEquals(entity1.hashCode(), entity2.hashCode()); // Same ID should have same hashCode
        assertNotEquals(entity1.hashCode(), entity3.hashCode()); // Different ID should have different hashCode
    }

    @Test
    void shouldImplementToString() {
        // Arrange
        String id = UUID.randomUUID().toString();
        GameEntity gameEntity = GameEntity.builder()
                .id(id)
                .title("The Last of Us Part II")
                .build();

        // Act
        String toString = gameEntity.toString();

        // Assert
        assertNotNull(toString);
        assertFalse(toString.isEmpty());
        assertTrue(toString.contains("GameEntity"));
        assertTrue(toString.contains(id));
        assertTrue(toString.contains("The Last of Us Part II"));
    }

    @Test
    void shouldCreateGameEntityWithAllArgsConstructor() {
        // Arrange
        Long dbId = 3L;
        String id = UUID.randomUUID().toString();
        String title = "Elden Ring";
        String platform = "PlayStation 5";
        String genre = "RPG";
        String developer = "FromSoftware";
        LocalDate releaseDate = LocalDate.of(2022, 2, 25);
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now().plusDays(1);

        // Create a base entity first to pass to the constructor
        BaseEntity baseEntity = new BaseEntity(dbId, createdAt, updatedAt);

        // Act
        GameEntity gameEntity = new GameEntity(
                id,
                title,
                platform,
                genre,
                developer,
                releaseDate
        );

        // Set the base entity properties
        gameEntity.setDbId(baseEntity.getDbId());
        gameEntity.setCreatedAt(baseEntity.getCreatedAt());
        gameEntity.setUpdatedAt(baseEntity.getUpdatedAt());

        // Assert
        assertEquals(dbId, gameEntity.getDbId());
        assertEquals(id, gameEntity.getId());
        assertEquals(title, gameEntity.getTitle());
        assertEquals(platform, gameEntity.getPlatform());
        assertEquals(genre, gameEntity.getGenre());
        assertEquals(developer, gameEntity.getDeveloper());
        assertEquals(releaseDate, gameEntity.getReleaseDate());
        assertEquals(createdAt, gameEntity.getCreatedAt());
        assertEquals(updatedAt, gameEntity.getUpdatedAt());
    }
}
