package com.tracktainment.gamemanager.dto.duxmanager.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssetResponse {

    private String id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String externalId;
    private String type;
    private PermissionPolicy permissionPolicy;
    private ArtifactInformation artifactInformation;

    @ToString
    @Getter
    @RequiredArgsConstructor
    public enum PermissionPolicy {

        OWNER("owner"),
        VIEWER("viewer");

        private final String value;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ArtifactInformation {

        private String groupId;
        private String artifactId;
        private String version;
    }
}
