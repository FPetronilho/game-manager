package com.tracktainment.gamemanager.usecases;

import com.tracktainment.gamemanager.dataprovider.DuxManagerDataProvider;
import com.tracktainment.gamemanager.dataprovider.GameDataProvider;
import com.tracktainment.gamemanager.domain.Game;
import com.tracktainment.gamemanager.dto.duxmanager.response.AssetResponse;
import com.tracktainment.gamemanager.exception.ResourceNotFoundException;
import com.tracktainment.gamemanager.security.context.DigitalUser;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindByIdUseCase {

    private final GameDataProvider gameDataProvider;
    private final DuxManagerDataProvider duxManagerDataProvider;

    public Output execute(Input input) {
        // Get the asset from Dux Manager
        DigitalUser digitalUser = new DigitalUser();
        digitalUser.setId("252f4696-ebbe-48da-8d7a-6b91ba216a14");

        List<AssetResponse> assetResponseList = duxManagerDataProvider.findAssetsByCriteria(
                0,
                10,
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
