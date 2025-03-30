package com.tracktainment.gamemanager.usecases;

import com.tracktainment.gamemanager.dataprovider.GameDataProvider;
import com.tracktainment.gamemanager.domain.Game;
import lombok.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindByIdUseCase {

    private final GameDataProvider gameDataProvider;

    public Output execute(Input input) {
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
