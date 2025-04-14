package com.tracktainment.gamemanager.usecases;

import com.tracktainment.gamemanager.dataprovider.DuxManagerDataProvider;
import com.tracktainment.gamemanager.dataprovider.GameDataProvider;
import com.tracktainment.gamemanager.domain.Game;
import com.tracktainment.gamemanager.dto.duxmanager.response.AssetResponse;
import com.tracktainment.gamemanager.exception.ResourceNotFoundException;
import com.tracktainment.gamemanager.security.context.DigitalUser;
import com.tracktainment.gamemanager.security.util.SecurityUtil;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindByIdUseCase {

    private final GameDataProvider gameDataProvider;
    private final DuxManagerDataProvider duxManagerDataProvider;
    private final SecurityUtil securityUtil;

    public Output execute(Input input) {
        // Get digital user from jwt
        DigitalUser digitalUser = securityUtil.getDigitalUser();

        // Get asset from dux-manager
        List<AssetResponse> assetResponseList = duxManagerDataProvider.findAssetsByCriteria(
                input.getJwt(),
                digitalUser.getId(),
                input.getId(),
                "com.tracktainment",
                "game-manager",
                "game",
                null,
                null,
                null
        );

        // If asset does not exist then throw exception
        if (CollectionUtils.isEmpty(assetResponseList)) {
            throw new ResourceNotFoundException(Game.class, input.getId());
        }

        // Find and return the game
        return Output.builder()
                .game(gameDataProvider.findById(input.getId()))
                .build();
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class Input {
        private String jwt;
        private String id;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class Output {
        private Game game;
    }
}
