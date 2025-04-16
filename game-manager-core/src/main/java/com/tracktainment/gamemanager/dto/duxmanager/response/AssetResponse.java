package com.tracktainment.gamemanager.dto.duxmanager.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Asset response information from DUX Manager")
public class AssetResponse {

    @Schema(description = "Unique identifier", example = "123e4567-e89b-12d3-a456-426614174000")
    private String id;

    @Schema(description = "Creation timestamp", example = "2023-04-15T14:30:45")
    private LocalDateTime createdAt;

    @Schema(description = "Last update timestamp", example = "2023-05-20T09:12:33")
    private LocalDateTime updatedAt;

    @Schema(description = "External ID from the source system", example = "123e4567-e89b-12d3-a456-426614174000")
    private String externalId;

    @Schema(description = "Type of asset", example = "game")
    private String type;

    @Schema(description = "Permission policy for asset")
    private PermissionPolicy permissionPolicy;

    @Schema(description = "Artifact information")
    private ArtifactInformation artifactInformation;

    @ToString
    @Getter
    @RequiredArgsConstructor
    @Schema(description = "Permission policy types")
    public enum PermissionPolicy {

        @Schema(description = "User owns this asset")
        OWNER("owner"),

        @Schema(description = "User can only view this asset")
        VIEWER("viewer");

        private final String value;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Information about the artifact")
    public static class ArtifactInformation {

        @Schema(description = "Group ID of the artifact", example = "com.tracktainment")
        private String groupId;

        @Schema(description = "Artifact ID", example = "game-manager")
        private String artifactId;

        @Schema(description = "Version of the artifact", example = "0.0.1-SNAPSHOT")
        private String version;
    }
}
