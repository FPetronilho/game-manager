package com.tracktainment.gamemanager.usecases;

import com.tracktainment.gamemanager.dataprovider.GameDataProvider;
import com.tracktainment.gamemanager.domain.Game;
import com.tracktainment.gamemanager.dto.GameUpdate;
import lombok.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUseCase {

    private final GameDataProvider gameDataProvider;
    private final FindByIdUseCase findByIdUseCase;

    public Output execute(Input input) {
        /* Finds the game to update. Conditions necessary to retrieve the asset from Dux Manager, i.e. authenticate the
        digital user, are already being processed in FindByIdUseCase
         */
        Game game = findByIdUseCase.execute(
                FindByIdUseCase.Input.builder()
                        .id(input.getId())
                        .build()
        ).getGame();

        /* Update and return the updated game. No action is necessary on Dux Manage as it only stores information on
        what assets each digital user has. It does not contain information of the game itself.
         */
        return Output.builder()
                .game(gameDataProvider.update(input.getId(), input.getGameUpdate()))
                .build();
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class Input {
        private String id;
        private GameUpdate gameUpdate;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class Output {
        private Game game;
    }
}
