package com.tracktainment.gamemanager.usecases;

import com.tracktainment.gamemanager.dataprovider.GameDataProvider;
import com.tracktainment.gamemanager.domain.Game;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ListByCriteriaUseCase {

    private final GameDataProvider gameDataProvider;

    public Output execute(Input input) {
        inputTreatment(input);
        inputValidation(input);

        return Output.builder()
                .games(gameDataProvider.listByCriteria(input))
                .build();
    }

    private void inputTreatment(Input input) {
        if (input.getCreatedAt() != null) {
            input.setTo(null);
            input.setFrom(null);
        }
    }

    private void inputValidation(Input input) {
        if (input.getTo() != null && input.getFrom() != null && input.getTo().isBefore(input.getFrom())) {

        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class Input {
        private String title;
        private String platform;
        private String genre;
        private String developer;
        private LocalDate releaseDate;
        private LocalDate createdAt;
        private LocalDate from;
        private LocalDate to;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class Output {
        private List<Game> games;
    }
}
