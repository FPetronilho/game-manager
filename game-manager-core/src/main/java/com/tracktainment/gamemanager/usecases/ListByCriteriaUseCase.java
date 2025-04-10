package com.tracktainment.gamemanager.usecases;

import com.tracktainment.gamemanager.dataprovider.DuxManagerDataProvider;
import com.tracktainment.gamemanager.dataprovider.GameDataProvider;
import com.tracktainment.gamemanager.domain.Game;
import com.tracktainment.gamemanager.domain.OrderBy;
import com.tracktainment.gamemanager.domain.OrderDirection;
import com.tracktainment.gamemanager.dto.duxmanager.response.AssetResponse;
import com.tracktainment.gamemanager.security.context.DigitalUser;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ListByCriteriaUseCase {

    private final GameDataProvider gameDataProvider;
    private final DuxManagerDataProvider duxManagerDataProvider;

    public Output execute(Input input) {
        // Get the assets from Dux Manager
        DigitalUser digitalUser = new DigitalUser();
        digitalUser.setId("1b63e584-8921-4bfe-bbcd-c04caa3e0790");

        List<AssetResponse> assetResponseList = duxManagerDataProvider.findAssetsByCriteria(
                input.getOffset(),
                input.getLimit(),
                digitalUser.getId(),
                String.join(",", input.getIds()),
                "com.tracktainment",
                "game-manager",
                "game",
                null,
                null,
                null
        );

        // Get a list of external IDs of the received assets
        List<String> assetIds = assetResponseList.stream()
                .map(AssetResponse::getExternalId)
                .toList();

        input.setIds(assetIds);
        return Output.builder()
                .games(gameDataProvider.listByCriteria(input))
                .build();
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class Input {
        private Integer offset;
        private Integer limit;
        private List<String> ids;
        private String title;
        private String platform;
        private String genre;
        private String developer;
        private LocalDate releaseDate;
        private LocalDate createdAt;
        private LocalDate from;
        private LocalDate to;
        private List<OrderBy> orderByList;
        private List<OrderDirection> orderDirectionList;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class Output {
        private List<Game> games;
    }
}
