package testutil;

import com.tracktainment.gamemanager.dto.duxmanager.request.AssetRequest;
import com.tracktainment.gamemanager.dto.duxmanager.response.AssetResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class TestGameDataUtil {
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
