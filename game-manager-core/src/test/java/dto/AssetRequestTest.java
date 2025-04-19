package dto;

import com.tracktainment.gamemanager.dto.duxmanager.request.AssetRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AssetRequestTest {

    @Test
    void shouldCreateAssetRequestUsingBuilder() {
        // Arrange
        String externalId = "123e4567-e89b-12d3-a456-426614174000";
        String type = "game";
        AssetRequest.PermissionPolicy permissionPolicy = AssetRequest.PermissionPolicy.OWNER;
        AssetRequest.ArtifactInformation artifactInformation = new AssetRequest.ArtifactInformation(
                "com.tracktainment",
                "game-manager",
                "0.0.1-SNAPSHOT"
        );

        // Act
        AssetRequest assetRequest = AssetRequest.builder()
                .externalId(externalId)
                .type(type)
                .permissionPolicy(permissionPolicy)
                .artifactInformation(artifactInformation)
                .build();

        // Assert
        assertEquals(externalId, assetRequest.getExternalId());
        assertEquals(type, assetRequest.getType());
        assertEquals(permissionPolicy, assetRequest.getPermissionPolicy());
        assertEquals(artifactInformation, assetRequest.getArtifactInformation());
    }

    @Test
    void shouldCreateEmptyAssetRequestUsingNoArgsConstructor() {
        // Act
        AssetRequest assetRequest = new AssetRequest();

        // Assert
        assertNull(assetRequest.getExternalId());
        assertNull(assetRequest.getType());
        assertNull(assetRequest.getPermissionPolicy());
        assertNull(assetRequest.getArtifactInformation());
    }

    @Test
    void shouldCreateAssetRequestUsingAllArgsConstructor() {
        // Arrange
        String externalId = "123e4567-e89b-12d3-a456-426614174000";
        String type = "game";
        AssetRequest.PermissionPolicy permissionPolicy = AssetRequest.PermissionPolicy.OWNER;
        AssetRequest.ArtifactInformation artifactInformation = new AssetRequest.ArtifactInformation(
                "com.tracktainment",
                "game-manager",
                "0.0.1-SNAPSHOT"
        );

        // Act
        AssetRequest assetRequest = new AssetRequest(externalId, type, permissionPolicy, artifactInformation);

        // Assert
        assertEquals(externalId, assetRequest.getExternalId());
        assertEquals(type, assetRequest.getType());
        assertEquals(permissionPolicy, assetRequest.getPermissionPolicy());
        assertEquals(artifactInformation, assetRequest.getArtifactInformation());
    }

    @Test
    void shouldUseSettersAndGetters() {
        // Arrange
        AssetRequest assetRequest = new AssetRequest();
        String externalId = "123e4567-e89b-12d3-a456-426614174000";
        String type = "game";
        AssetRequest.PermissionPolicy permissionPolicy = AssetRequest.PermissionPolicy.OWNER;
        AssetRequest.ArtifactInformation artifactInformation = new AssetRequest.ArtifactInformation(
                "com.tracktainment",
                "game-manager",
                "0.0.1-SNAPSHOT"
        );

        // Act
        assetRequest.setExternalId(externalId);
        assetRequest.setType(type);
        assetRequest.setPermissionPolicy(permissionPolicy);
        assetRequest.setArtifactInformation(artifactInformation);

        // Assert
        assertEquals(externalId, assetRequest.getExternalId());
        assertEquals(type, assetRequest.getType());
        assertEquals(permissionPolicy, assetRequest.getPermissionPolicy());
        assertEquals(artifactInformation, assetRequest.getArtifactInformation());
    }

    @Test
    void shouldImplementEqualsAndHashCode() {
        // Arrange
        AssetRequest assetRequest1 = AssetRequest.builder()
                .externalId("123e4567-e89b-12d3-a456-426614174000")
                .type("game")
                .permissionPolicy(AssetRequest.PermissionPolicy.OWNER)
                .artifactInformation(new AssetRequest.ArtifactInformation(
                        "com.tracktainment",
                        "game-manager",
                        "0.0.1-SNAPSHOT"
                ))
                .build();

        AssetRequest assetRequest2 = AssetRequest.builder()
                .externalId("123e4567-e89b-12d3-a456-426614174000")
                .type("game")
                .permissionPolicy(AssetRequest.PermissionPolicy.OWNER)
                .artifactInformation(new AssetRequest.ArtifactInformation(
                        "com.tracktainment",
                        "game-manager",
                        "0.0.1-SNAPSHOT"
                ))
                .build();

        AssetRequest assetRequest3 = AssetRequest.builder()
                .externalId("different-id")
                .type("game")
                .permissionPolicy(AssetRequest.PermissionPolicy.OWNER)
                .artifactInformation(new AssetRequest.ArtifactInformation(
                        "com.tracktainment",
                        "game-manager",
                        "0.0.1-SNAPSHOT"
                ))
                .build();

        // Assert
        assertEquals(assetRequest1, assetRequest2); // Equal objects
        assertNotEquals(assetRequest1, assetRequest3); // Different objects
        assertEquals(assetRequest1.hashCode(), assetRequest2.hashCode()); // Equal hash codes
        assertNotEquals(assetRequest1.hashCode(), assetRequest3.hashCode()); // Different hash codes
    }

    @Test
    void shouldImplementToString() {
        // Arrange
        AssetRequest assetRequest = AssetRequest.builder()
                .externalId("123e4567-e89b-12d3-a456-426614174000")
                .type("game")
                .permissionPolicy(AssetRequest.PermissionPolicy.OWNER)
                .artifactInformation(new AssetRequest.ArtifactInformation(
                        "com.tracktainment",
                        "game-manager",
                        "0.0.1-SNAPSHOT"
                ))
                .build();

        // Act
        String toString = assetRequest.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("AssetRequest"));
        assertTrue(toString.contains("123e4567-e89b-12d3-a456-426614174000"));
        assertTrue(toString.contains("game"));
        assertTrue(toString.contains("OWNER"));
    }

    @Test
    void permissionPolicyShouldHaveCorrectValues() {
        // Assert
        assertEquals("owner", AssetRequest.PermissionPolicy.OWNER.getValue());
        assertEquals("viewer", AssetRequest.PermissionPolicy.VIEWER.getValue());
    }

    @Test
    void permissionPolicyShouldImplementToString() {
        // Assert
        assertTrue(AssetRequest.PermissionPolicy.OWNER.toString().contains("OWNER"));
        assertTrue(AssetRequest.PermissionPolicy.VIEWER.toString().contains("VIEWER"));
    }

    @Test
    void shouldCreateArtifactInformationUsingBuilder() {
        // Arrange
        String groupId = "com.tracktainment";
        String artifactId = "game-manager";
        String version = "0.0.1-SNAPSHOT";

        // Act
        AssetRequest.ArtifactInformation artifactInformation = AssetRequest.ArtifactInformation.builder()
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
        AssetRequest.ArtifactInformation artifactInformation = new AssetRequest.ArtifactInformation();

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
        AssetRequest.ArtifactInformation artifactInformation = new AssetRequest.ArtifactInformation(
                groupId, artifactId, version);

        // Assert
        assertEquals(groupId, artifactInformation.getGroupId());
        assertEquals(artifactId, artifactInformation.getArtifactId());
        assertEquals(version, artifactInformation.getVersion());
    }

    @Test
    void artifactInformationShouldUseSettersAndGetters() {
        // Arrange
        AssetRequest.ArtifactInformation artifactInformation = new AssetRequest.ArtifactInformation();
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
        AssetRequest.ArtifactInformation info1 = new AssetRequest.ArtifactInformation(
                "com.tracktainment", "game-manager", "0.0.1-SNAPSHOT");
        AssetRequest.ArtifactInformation info2 = new AssetRequest.ArtifactInformation(
                "com.tracktainment", "game-manager", "0.0.1-SNAPSHOT");
        AssetRequest.ArtifactInformation info3 = new AssetRequest.ArtifactInformation(
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
        AssetRequest.ArtifactInformation artifactInformation = new AssetRequest.ArtifactInformation(
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
