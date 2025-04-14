package com.tracktainment.gamemanager.usecases;

import com.tracktainment.gamemanager.dataprovider.DuxManagerDataProvider;
import com.tracktainment.gamemanager.dataprovider.GameDataProvider;
import com.tracktainment.gamemanager.security.context.DigitalUser;
import com.tracktainment.gamemanager.security.util.SecurityUtil;
import lombok.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUseCase {

    private final GameDataProvider gameDataProvider;
    private final DuxManagerDataProvider duxManagerDataProvider;
    private final SecurityUtil securityUtil;

    public void execute(Input input) {
        // Get digital user from jwt
        DigitalUser digitalUser = securityUtil.getDigitalUser();

        // Delete the asset from dux-manager
        duxManagerDataProvider.deleteAsset(
                input.getJwt(),
                digitalUser.getId(),
                input.getId()
        );

        // TODO: Should there not be a condition to verify if asset was deleted? Like checking the HTTP status code?

        // Delete the game
        gameDataProvider.delete(input.getId());
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class Input {
        private String jwt;
        private String id;
    }
}
