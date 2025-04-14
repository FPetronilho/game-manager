package com.tracktainment.gamemanager.usecases;

import com.tracktainment.gamemanager.dataprovider.DuxManagerDataProvider;
import com.tracktainment.gamemanager.dataprovider.GameDataProvider;
import com.tracktainment.gamemanager.domain.Game;
import com.tracktainment.gamemanager.dto.GameCreate;
import com.tracktainment.gamemanager.mapper.AssetMapper;
import com.tracktainment.gamemanager.security.context.DigitalUser;
import com.tracktainment.gamemanager.security.util.SecurityUtil;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateUseCase {

    private final GameDataProvider gameDataProvider;
    private final DuxManagerDataProvider duxManagerDataProvider;
    private final SecurityUtil securityUtil;

    public Output execute(Input input) {
        // Create game
        Game game = gameDataProvider.create(input.getGameCreate());

        // Get digital user from jwt and create game asset in dux-manager
        try {
            DigitalUser digitalUser = securityUtil.getDigitalUser();

            duxManagerDataProvider.createAsset(
                    input.getJwt(),
                    digitalUser.getId(),
                    AssetMapper.toAssetRequest(game)
            );

        // If asset cannot be created on dux-manager then rollback create game
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
        private String jwt;
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
