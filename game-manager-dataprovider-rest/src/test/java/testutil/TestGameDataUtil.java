package testutil;

import com.tracktainment.gamemanager.domain.Game;
import com.tracktainment.gamemanager.dto.GameCreate;
import com.tracktainment.gamemanager.dto.GameUpdate;
import com.tracktainment.gamemanager.dto.duxmanager.request.AssetRequest;
import com.tracktainment.gamemanager.dto.duxmanager.response.AssetResponse;
import com.tracktainment.gamemanager.security.context.DigitalUser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class TestGameDataUtil {

    public static GameCreate createTestGameCreate() {
        return GameCreate.builder()
                .title("The Last of Us Part II")
                .platform("PlayStation 5")
                .genre("Action")
                .developer("Naughty Dog")
                .releaseDate(LocalDate.of(2020, 6, 19))
                .build();
    }

    public static GameUpdate createTestGameUpdate() {
        return GameUpdate.builder()
                .title("The Last of Us Part II: Remastered")
                .platform("PlayStation 5")
                .genre("Action Adventure")
                .developer("Naughty Dog")
                .releaseDate(LocalDate.of(2021, 6, 19))
                .build();
    }

    public static Game createTestGame() {
        return Game.builder()
                .id(UUID.randomUUID().toString())
                .title("The Last of Us Part II")
                .platform("PlayStation 5")
                .genre("Action")
                .developer("Naughty Dog")
                .releaseDate(LocalDate.of(2020, 6, 19))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static Game createTestGameWithUpdate() {
        return Game.builder()
                .id(UUID.randomUUID().toString())
                .title("The Last of Us Part II: Remastered")
                .platform("PlayStation 5")
                .genre("Action Adventure")
                .developer("Naughty Dog")
                .releaseDate(LocalDate.of(2021, 6, 19))
                .createdAt(LocalDateTime.now().minusDays(10))
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static Game createTestGameWithId(String id) {
        return Game.builder()
                .id(id)
                .title("The Last of Us Part II")
                .platform("PlayStation 5")
                .genre("Action")
                .developer("Naughty Dog")
                .releaseDate(LocalDate.of(2020, 6, 19))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static DigitalUser createTestDigitalUser() {
        return DigitalUser.builder()
                .id(UUID.randomUUID().toString())
                .subject("auth2|123456789")
                .build();
    }

    public static AssetResponse createTestAssetResponse() {
        return AssetResponse.builder()
                .id(UUID.randomUUID().toString())
                .externalId(UUID.randomUUID().toString())
                .type("game")
                .permissionPolicy(AssetResponse.PermissionPolicy.OWNER)
                .artifactInformation(AssetResponse.ArtifactInformation.builder()
                        .groupId("com.tracktainment")
                        .artifactId("game-manager")
                        .version("0.0.1-SNAPSHOT")
                        .build())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static AssetRequest createTestAssetRequest(String gameId) {
        return AssetRequest.builder()
                .externalId(gameId)
                .type("game")
                .permissionPolicy(AssetRequest.PermissionPolicy.OWNER)
                .artifactInformation(AssetRequest.ArtifactInformation.builder()
                        .groupId("com.tracktainment")
                        .artifactId("game-manager")
                        .version("0.0.1-SNAPSHOT")
                        .build())
                .build();
    }
}
