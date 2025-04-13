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
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListByCriteriaUseCase {

    private final GameDataProvider gameDataProvider;
    private final DuxManagerDataProvider duxManagerDataProvider;

    public Output execute(Input input) {
        // Get the assets from Dux Manager
        DigitalUser digitalUser = new DigitalUser();
        digitalUser.setId("bd30e6d3-d51f-4548-910f-c93a25437259");

        List<AssetResponse> assetResponseList = duxManagerDataProvider.findAssetsByCriteria(
                digitalUser.getId(),
                input.getIds(),
                "com.tracktainment",
                "game-manager",
                "game",
                input.getCreatedAt(),
                input.getFrom(),
                input.getTo()
        );

        // Get IDs of the received assets
        String assetIds = assetResponseList.stream()
                .map(AssetResponse::getExternalId)
                .collect(Collectors.joining(","));

        boolean idsInputIsEmpty = input.getIds() == null;
        if (!StringUtils.hasText(assetIds)) {
            if (!idsInputIsEmpty) {
                assetIds = input.getIds();
            } else {
                assetIds = null;
            }
        }

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
        private String ids;
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
