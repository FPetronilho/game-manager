package mapper;

import com.tracktainment.gamemanager.domain.Game;
import com.tracktainment.gamemanager.dto.duxmanager.request.AssetRequest;
import com.tracktainment.gamemanager.mapper.AssetMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import testutil.TestGameDataUtil;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AssetMapperTest {

    @Test
    void shouldMapGameToAssetRequest() {
        // Arrange
        String gameId = UUID.randomUUID().toString();
        Game game = TestGameDataUtil.createTestGameWithId(gameId);

        // Act
        AssetRequest result = AssetMapper.toAssetRequest(game);

        // Assert
        assertNotNull(result);
        assertEquals(gameId, result.getExternalId());
        assertEquals("game", result.getType());
        assertEquals(AssetRequest.PermissionPolicy.OWNER, result.getPermissionPolicy());

        assertNotNull(result.getArtifactInformation());
        assertEquals("com.tracktainment", result.getArtifactInformation().getGroupId());
        assertEquals("game-manager", result.getArtifactInformation().getArtifactId());
        assertEquals("0.0.1-SNAPSHOT", result.getArtifactInformation().getVersion());
    }

    @Test
    void shouldHandleNullGameId() {
        // Arrange
        Game game = Game.builder()
                .id(null)
                .title("Test Game")
                .build();

        // Act
        AssetRequest result = AssetMapper.toAssetRequest(game);

        // Assert
        assertNotNull(result);
        assertNull(result.getExternalId());
        assertEquals("game", result.getType());
        assertEquals(AssetRequest.PermissionPolicy.OWNER, result.getPermissionPolicy());
    }

    @Test
    void shouldSetCorrectPermissionPolicy() {
        // Arrange
        Game game = TestGameDataUtil.createTestGame();

        // Act
        AssetRequest result = AssetMapper.toAssetRequest(game);

        // Assert
        assertEquals(AssetRequest.PermissionPolicy.OWNER, result.getPermissionPolicy());
    }

    @Test
    void shouldSetCorrectArtifactInformation() {
        // Arrange
        Game game = TestGameDataUtil.createTestGame();

        // Act
        AssetRequest result = AssetMapper.toAssetRequest(game);

        // Assert
        assertNotNull(result.getArtifactInformation());
        assertEquals("com.tracktainment", result.getArtifactInformation().getGroupId());
        assertEquals("game-manager", result.getArtifactInformation().getArtifactId());
        assertEquals("0.0.1-SNAPSHOT", result.getArtifactInformation().getVersion());
    }
}
