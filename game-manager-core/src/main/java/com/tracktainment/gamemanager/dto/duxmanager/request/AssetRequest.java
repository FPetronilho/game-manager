package com.tracktainment.gamemanager.dto.duxmanager.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tracktainment.gamemanager.dto.duxmanager.response.AssetResponse;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssetRequest {

    private String externalId;
    private String type;
    private AssetResponse.PermissionPolicy permissionPolicy;
    private AssetResponse.ArtifactInformation artifactInformation;

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
