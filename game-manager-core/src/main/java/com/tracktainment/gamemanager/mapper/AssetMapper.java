package com.tracktainment.gamemanager.mapper;

import com.tracktainment.gamemanager.domain.Game;
import com.tracktainment.gamemanager.dto.duxmanager.request.AssetRequest;

public class AssetMapper {

    public static AssetRequest toAssetRequest(Game game) {
        return AssetRequest.builder()
                .externalId(game.getId())
                .type("game")
                .artifactInformation(
                        new AssetRequest.ArtifactInformation(
                                "com.tracktainment",
                                "game-manager",
                                "0.0.1-SNAPSHOT"
                        )
                )
                .permissionPolicy(AssetRequest.PermissionPolicy.OWNER)
                .build();
    }
}
