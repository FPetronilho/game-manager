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

    public Output execute(Input input) {
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
