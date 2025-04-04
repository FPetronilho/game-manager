package com.tracktainment.gamemanager.usecases;

import com.tracktainment.gamemanager.dataprovider.DuxManagerDataProvider;
import com.tracktainment.gamemanager.dataprovider.GameDataProvider;
import com.tracktainment.gamemanager.security.context.DigitalUser;
import lombok.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUseCase {

    private final GameDataProvider gameDataProvider;
    private final DuxManagerDataProvider duxManagerDataProvider;

    public void execute(Input input) {
        // Delete the asset in Dux Manager
        DigitalUser digitalUser = new DigitalUser();
        digitalUser.setId("152f4696-ebbe-48da-8d7a-6b91ba216a14");
        duxManagerDataProvider.deleteAsset(digitalUser.getId(), input.getId());

        // TODO: Should there not be a condition to verify if asset was deleted? Like checking the HTTP status code?

        // Delete the game
        gameDataProvider.delete(input.getId());
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class Input {
        private String id;
    }
}
