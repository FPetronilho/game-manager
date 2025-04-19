package dto;

import com.tracktainment.gamemanager.dto.duxmanager.response.AssetResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AssetResponseTest {

    @Test
    void shouldCreateAssetResponseUsingBuilder() {
        // Arrange
        String id = "123e4567-e89b-12d3-a456-426614174000";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now().plusDays(1);
        String externalId = "456e7890-e12b-34d5-a678-426614174000";
        String type = "game";
        AssetResponse.PermissionPolicy permissionPolicy = AssetResponse.PermissionPolicy.OWNER;
        AssetResponse.ArtifactInformation artifactInformation = new AssetResponse.ArtifactInformation(
                "com.tracktainment",
                "game-manager",
                "0.0.1-SNAPSHOT"
        );

        // Act
        AssetResponse assetResponse = AssetResponse.builder()
                .id(id)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .externalId(externalId)
                .type(type)
                .permissionPolicy(permissionPolicy)
                .artifactInformation(artifactInformation)
                .build();

        // Assert
        assertEquals(id, assetResponse.getId());
        assertEquals(createdAt, assetResponse.getCreatedAt());
        assertEquals(updatedAt, assetResponse.getUpdatedAt());
        assertEquals(externalId, assetResponse.getExternalId());
        assertEquals(type, assetResponse.getType());
        assertEquals(permissionPolicy, assetResponse.getPermissionPolicy());
        assertEquals(artifactInformation, assetResponse.getArtifactInformation());
    }

    @Test
    void shouldCreateEmptyAssetResponseUsingNoArgsConstructor() {
        // Act
        AssetResponse assetResponse = new AssetResponse();

        // Assert
        assertNull(assetResponse.getId());
        assertNull(assetResponse.getCreatedAt());
        assertNull(assetResponse.getUpdatedAt());
        assertNull(assetResponse.getExternalId());
        assertNull(assetResponse.getType());
        assertNull(assetResponse.getPermissionPolicy());
        assertNull(assetResponse.getArtifactInformation());
    }

    @Test
    void shouldCreateAssetResponseUsingAllArgsConstructor() {
        // Arrange
        String id = "123e4567-e89b-12d3-a456-426614174000";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now().plusDays(1);
        String externalId = "456e7890-e12b-34d5-a678-426614174000";
        String type = "game";
        AssetResponse.PermissionPolicy permissionPolicy = AssetResponse.PermissionPolicy.OWNER;
        AssetResponse.ArtifactInformation artifactInformation = new AssetResponse.ArtifactInformation(
                "com.tracktainment",
                "game-manager",
                "0.0.1-SNAPSHOT"
        );

        // Act
        AssetResponse assetResponse = new AssetResponse(
                id, createdAt, updatedAt, externalId, type, permissionPolicy, artifactInformation);

        // Assert
        assertEquals(id, assetResponse.getId());
        assertEquals(createdAt, assetResponse.getCreatedAt());
        assertEquals(updatedAt, assetResponse.getUpdatedAt());
        assertEquals(externalId, assetResponse.getExternalId());
        assertEquals(type, assetResponse.getType());
        assertEquals(permissionPolicy, assetResponse.getPermissionPolicy());
        assertEquals(artifactInformation, assetResponse.getArtifactInformation());
    }

    @Test
    void shouldUseSettersAndGetters() {
        // Arrange
        AssetResponse assetResponse = new AssetResponse();
        String id = "123e4567-e89b-12d3-a456-426614174000";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now().plusDays(1);
        String externalId = "456e7890-e12b-34d5-a678-426614174000";
        String type = "game";
        AssetResponse.PermissionPolicy permissionPolicy = AssetResponse.PermissionPolicy.OWNER;
        AssetResponse.ArtifactInformation artifactInformation = new AssetResponse.ArtifactInformation(
                "com.tracktainment",
                "game-manager",
                "0.0.1-SNAPSHOT"
        );

        // Act
        assetResponse.setId(id);
        assetResponse.setCreatedAt(createdAt);
        assetResponse.setUpdatedAt(updatedAt);
        assetResponse.setExternalId(externalId);
        assetResponse.setType(type);
        assetResponse.setPermissionPolicy(permissionPolicy);
        assetResponse.setArtifactInformation(artifactInformation);

        // Assert
        assertEquals(id, assetResponse.getId());
        assertEquals(createdAt, assetResponse.getCreatedAt());
        assertEquals(updatedAt, assetResponse.getUpdatedAt());
        assertEquals(externalId, assetResponse.getExternalId());
        assertEquals(type, assetResponse.getType());
        assertEquals(permissionPolicy, assetResponse.getPermissionPolicy());
        assertEquals(artifactInformation, assetResponse.getArtifactInformation());
    }

    @Test
    void shouldImplementEqualsAndHashCode() {
        // Arrange
        AssetResponse assetResponse1 = AssetResponse.builder()
                .id("123e4567-e89b-12d3-a456-426614174000")
                .externalId("456e7890-e12b-34d5-a678-426614174000")
                .type("game")
                .permissionPolicy(AssetResponse.PermissionPolicy.OWNER)
                .build();

        AssetResponse assetResponse2 = AssetResponse.builder()
                .id("123e4567-e89b-12d3-a456-426614174000")
                .externalId("456e7890-e12b-34d5-a678-426614174000")
                .type("game")
                .permissionPolicy(AssetResponse.PermissionPolicy.OWNER)
                .build();

        AssetResponse assetResponse3 = AssetResponse.builder()
                .id("different-id")
                .externalId("456e7890-e12b-34d5-a678-426614174000")
                .type("game")
                .permissionPolicy(AssetResponse.PermissionPolicy.OWNER)
                .build();

        // Assert
        assertEquals(assetResponse1, assetResponse2); // Equal objects
        assertNotEquals(assetResponse1, assetResponse3); // Different objects
        assertEquals(assetResponse1.hashCode(), assetResponse2.hashCode()); // Equal hash codes
        assertNotEquals(assetResponse1.hashCode(), assetResponse3.hashCode()); // Different hash codes
    }

    @Test
    void shouldImplementToString() {
        // Arrange
        AssetResponse assetResponse = AssetResponse.builder()
                .id("123e4567-e89b-12d3-a456-426614174000")
                .externalId("456e7890-e12b-34d5-a678-426614174000")
                .type("game")
                .permissionPolicy(AssetResponse.PermissionPolicy.OWNER)
                .build();

        // Act
        String toString = assetResponse.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("AssetResponse"));
        assertTrue(toString.contains("123e4567-e89b-12d3-a456-426614174000"));
        assertTrue(toString.contains("456e7890-e12b-34d5-a678-426614174000"));
        assertTrue(toString.contains("game"));
        assertTrue(toString.contains("OWNER"));
    }

    @Test
    void permissionPolicyShouldHaveCorrectValues() {
        // Assert
        assertEquals("owner", AssetResponse.PermissionPolicy.OWNER.getValue());
        assertEquals("viewer", AssetResponse.PermissionPolicy.VIEWER.getValue());
    }

    @Test
    void permissionPolicyShouldImplementToString() {
        // Assert
        assertTrue(AssetResponse.PermissionPolicy.OWNER.toString().contains("OWNER"));
        assertTrue(AssetResponse.PermissionPolicy.VIEWER.toString().contains("VIEWER"));
    }

    @Test
    void shouldCreateArtifactInformationUsingBuilder() {
        // Arrange
        String groupId = "com.tracktainment";
        String artifactId = "game-manager";
        String version = "0.0.1-SNAPSHOT";

        // Act
        AssetResponse.ArtifactInformation artifactInformation = AssetResponse.ArtifactInformation.builder()
                .groupId(groupId)
                .artifactId(artifactId)
                .version(version)
                .build();

        // Assert
        assertEquals(groupId, artifactInformation.getGroupId());
        assertEquals(artifactId, artifactInformation.getArtifactId());
        assertEquals(version, artifactInformation.getVersion());
    }

    @Test
    void shouldCreateEmptyArtifactInformationUsingNoArgsConstructor() {
        // Act
        AssetResponse.ArtifactInformation artifactInformation = new AssetResponse.ArtifactInformation();

        // Assert
        assertNull(artifactInformation.getGroupId());
        assertNull(artifactInformation.getArtifactId());
        assertNull(artifactInformation.getVersion());
    }

    @Test
    void shouldCreateArtifactInformationUsingAllArgsConstructor() {
        // Arrange
        String groupId = "com.tracktainment";
        String artifactId = "game-manager";
        String version = "0.0.1-SNAPSHOT";

        // Act
        AssetResponse.ArtifactInformation artifactInformation = new AssetResponse.ArtifactInformation(
                groupId, artifactId, version);

        // Assert
        assertEquals(groupId, artifactInformation.getGroupId());
        assertEquals(artifactId, artifactInformation.getArtifactId());
        assertEquals(version, artifactInformation.getVersion());
    }

    @Test
    void artifactInformationShouldUseSettersAndGetters() {
        // Arrange
        AssetResponse.ArtifactInformation artifactInformation = new AssetResponse.ArtifactInformation();
        String groupId = "com.tracktainment";
        String artifactId = "game-manager";
        String version = "0.0.1-SNAPSHOT";

        // Act
        artifactInformation.setGroupId(groupId);
        artifactInformation.setArtifactId(artifactId);
        artifactInformation.setVersion(version);

        // Assert
        assertEquals(groupId, artifactInformation.getGroupId());
        assertEquals(artifactId, artifactInformation.getArtifactId());
        assertEquals(version, artifactInformation.getVersion());
    }

    @Test
    void artifactInformationShouldImplementEqualsAndHashCode() {
        // Arrange
        AssetResponse.ArtifactInformation info1 = new AssetResponse.ArtifactInformation(
                "com.tracktainment", "game-manager", "0.0.1-SNAPSHOT");
        AssetResponse.ArtifactInformation info2 = new AssetResponse.ArtifactInformation(
                "com.tracktainment", "game-manager", "0.0.1-SNAPSHOT");
        AssetResponse.ArtifactInformation info3 = new AssetResponse.ArtifactInformation(
                "com.tracktainment", "different-artifact", "0.0.1-SNAPSHOT");

        // Assert
        assertEquals(info1, info2); // Equal objects
        assertNotEquals(info1, info3); // Different objects
        assertEquals(info1.hashCode(), info2.hashCode()); // Equal hash codes
        assertNotEquals(info1.hashCode(), info3.hashCode()); // Different hash codes
    }

    @Test
    void artifactInformationShouldImplementToString() {
        // Arrange
        AssetResponse.ArtifactInformation artifactInformation = new AssetResponse.ArtifactInformation(
                "com.tracktainment", "game-manager", "0.0.1-SNAPSHOT");

        // Act
        String toString = artifactInformation.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("ArtifactInformation"));
        assertTrue(toString.contains("com.tracktainment"));
        assertTrue(toString.contains("game-manager"));
        assertTrue(toString.contains("0.0.1-SNAPSHOT"));
    }
}
