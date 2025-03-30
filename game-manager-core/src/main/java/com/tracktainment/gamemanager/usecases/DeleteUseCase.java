package com.tracktainment.gamemanager.usecases;

import com.tracktainment.gamemanager.dataprovider.GameDataProvider;
import lombok.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUseCase {

    private final GameDataProvider gameDataProvider;

    public void execute(Input input) {
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
