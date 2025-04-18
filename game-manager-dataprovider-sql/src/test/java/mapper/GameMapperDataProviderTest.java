package mapper;

import com.tracktainment.gamemanager.domain.Game;
import com.tracktainment.gamemanager.dto.GameCreate;
import com.tracktainment.gamemanager.dto.GameUpdate;
import com.tracktainment.gamemanager.entity.GameEntity;
import com.tracktainment.gamemanager.mapper.GameMapperDataProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import testutil.TestGameDataUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GameMapperDataProviderTest {

    private final GameMapperDataProvider mapper = Mappers.getMapper(GameMapperDataProvider.class);

    // UUID pattern matcher
    private static final Pattern UUID_PATTERN =
            Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

    @Test
    void shouldMapGameEntityToGame() {
        // Arrange
        String id = UUID.randomUUID().toString();
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now().plusDays(1);
        LocalDate releaseDate = LocalDate.of(2020, 6, 19);

        GameEntity gameEntity = GameEntity.builder()
                .id(id)
                .title("The Last of Us Part II")
                .platform("PlayStation 5")
                .genre("Action")
                .developer("Naughty Dog")
                .releaseDate(releaseDate)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();

        // Act
        Game result = mapper.toGame(gameEntity);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("The Last of Us Part II", result.getTitle());
        assertEquals("PlayStation 5", result.getPlatform());
        assertEquals("Action", result.getGenre());
        assertEquals("Naughty Dog", result.getDeveloper());
        assertEquals(releaseDate, result.getReleaseDate());
        assertEquals(createdAt, result.getCreatedAt());
        assertEquals(updatedAt, result.getUpdatedAt());
    }

    @Test
    void shouldMapGameCreateToGameEntity() {
        // Arrange
        GameCreate gameCreate = TestGameDataUtil.createTestGameCreate();

        // Act
        GameEntity result = mapper.toGameEntity(gameCreate);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getId());
        assertTrue(UUID_PATTERN.matcher(result.getId()).matches(), "ID should be a valid UUID");
        assertEquals(gameCreate.getTitle(), result.getTitle());
        assertEquals(gameCreate.getPlatform(), result.getPlatform());
        assertEquals(gameCreate.getGenre(), result.getGenre());
        assertEquals(gameCreate.getDeveloper(), result.getDeveloper());
        assertEquals(gameCreate.getReleaseDate(), result.getReleaseDate());

        // These should be null as specified in @Mapping annotations
        assertNull(result.getDbId());
        assertNull(result.getCreatedAt());
        assertNull(result.getUpdatedAt());
    }

    @Test
    void shouldUpdateGameEntityFromGameUpdate() {
        // Arrange
        String id = UUID.randomUUID().toString();
        GameEntity gameEntity = GameEntity.builder()
                .id(id)
                .title("The Last of Us Part II")
                .platform("PlayStation 4")
                .genre("Action")
                .developer("Naughty Dog")
                .releaseDate(LocalDate.of(2020, 6, 19))
                .build();

        GameUpdate gameUpdate = TestGameDataUtil.createTestGameUpdate();

        // Act
        mapper.updateBookEntity(gameEntity, gameUpdate);

        // Assert
        assertEquals(id, gameEntity.getId()); // ID should not change
        assertEquals(gameUpdate.getTitle(), gameEntity.getTitle());
        assertEquals(gameUpdate.getPlatform(), gameEntity.getPlatform());
        assertEquals(gameUpdate.getGenre(), gameEntity.getGenre());
        assertEquals(gameUpdate.getDeveloper(), gameEntity.getDeveloper());
        assertEquals(gameUpdate.getReleaseDate(), gameEntity.getReleaseDate());

        // These should remain unchanged as specified in @Mapping annotations
        assertNull(gameEntity.getDbId());
        assertNull(gameEntity.getCreatedAt());
        assertNull(gameEntity.getUpdatedAt());
    }

    @Test
    void shouldHandleNullGameUpdateValues() {
        // Arrange
        String id = UUID.randomUUID().toString();
        LocalDate releaseDate = LocalDate.of(2020, 6, 19);
        GameEntity gameEntity = GameEntity.builder()
                .id(id)
                .title("The Last of Us Part II")
                .platform("PlayStation 4")
                .genre("Action")
                .developer("Naughty Dog")
                .releaseDate(releaseDate)
                .build();

        GameUpdate gameUpdate = GameUpdate.builder()
                .title("The Last of Us Part II: Remastered")
                // All other fields null
                .build();

        // Act
        mapper.updateBookEntity(gameEntity, gameUpdate);

        // Assert
        assertEquals(id, gameEntity.getId()); // ID should not change
        assertEquals("The Last of Us Part II: Remastered", gameEntity.getTitle());
        assertEquals("PlayStation 4", gameEntity.getPlatform()); // Should not change
        assertEquals("Action", gameEntity.getGenre()); // Should not change
        assertEquals("Naughty Dog", gameEntity.getDeveloper()); // Should not change
        assertEquals(releaseDate, gameEntity.getReleaseDate()); // Should not change
    }

    @Test
    void shouldHandleNullInput() {
        // Act & Assert
        assertNull(mapper.toGame(null));
    }

    @Test
    void shouldGenerateDifferentIdsForDifferentCalls() {
        // Arrange
        GameCreate gameCreate = TestGameDataUtil.createTestGameCreate();

        // Act
        GameEntity result1 = mapper.toGameEntity(gameCreate);
        GameEntity result2 = mapper.toGameEntity(gameCreate);

        // Assert
        assertNotNull(result1.getId());
        assertNotNull(result2.getId());
        assertNotEquals(result1.getId(), result2.getId(), "Generated IDs should be different");
    }
}
