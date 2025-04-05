package com.tracktainment.gamemanager.usecases;

import com.tracktainment.gamemanager.dataprovider.DuxManagerDataProvider;
import com.tracktainment.gamemanager.dataprovider.GameDataProvider;
import com.tracktainment.gamemanager.domain.Game;
import com.tracktainment.gamemanager.dto.GameCreate;
import com.tracktainment.gamemanager.dto.duxmanager.request.AssetRequest;
import com.tracktainment.gamemanager.mapper.AssetMapper;
import com.tracktainment.gamemanager.security.context.DigitalUser;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateUseCase {

    private final GameDataProvider gameDataProvider;
    private final DuxManagerDataProvider duxManagerDataProvider;

    public Output execute(Input input) {
        // Save game in the database and create AssetRequest
        Game game = gameDataProvider.create(input.getGameCreate());
        AssetRequest assetRequest = AssetMapper.toAssetRequest(game);

        // Save asset in Dux Manager
        try {
            DigitalUser digitalUser = new DigitalUser();
            digitalUser.setId("1b63e584-8921-4bfe-bbcd-c04caa3e0790");
            duxManagerDataProvider.createAsset(digitalUser.getId(), assetRequest);
        } catch (Exception e) {
            log.error("Could not create game in Dux Manager. Reason: {}", e.getMessage());
            gameDataProvider.delete(game.getId()); // Rollback save operation if it is not possible to save asset
            throw e;
        }

        return Output.builder()
                .game(game)
                .build();
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class Input {
        private GameCreate gameCreate;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class Output {
        private Game game;
    }
}
