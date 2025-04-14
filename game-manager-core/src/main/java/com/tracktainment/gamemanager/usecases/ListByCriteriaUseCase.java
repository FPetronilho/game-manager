package com.tracktainment.gamemanager.usecases;

import com.tracktainment.gamemanager.dataprovider.DuxManagerDataProvider;
import com.tracktainment.gamemanager.dataprovider.GameDataProvider;
import com.tracktainment.gamemanager.domain.Game;
import com.tracktainment.gamemanager.domain.OrderBy;
import com.tracktainment.gamemanager.domain.OrderDirection;
import com.tracktainment.gamemanager.dto.duxmanager.response.AssetResponse;
import com.tracktainment.gamemanager.security.context.DigitalUser;
import com.tracktainment.gamemanager.security.util.SecurityUtil;
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
    private final SecurityUtil securityUtil;

    public Output execute(Input input) {
        // Get digital user from jwt
        DigitalUser digitalUser = securityUtil.getDigitalUser();

        // Get assets by criteria from dux-manger
        List<AssetResponse> assetResponseList = duxManagerDataProvider.findAssetsByCriteria(
                input.getJwt(),
                digitalUser.getId(),
                input.getIds(),
                "com.tracktainment",
                "game-manager",
                "game",
                input.getCreatedAt(),
                input.getFrom(),
                input.getTo()
        );

        // Convert list of assets to String so that it can be replaced in "ids" input
        String assetIds = assetResponseList.stream()
                .map(AssetResponse::getExternalId)
                .collect(Collectors.joining(","));

        // Check if "ids" input is empty because no assets match or, because that criteria was never inputted
        boolean idsInputIsEmpty = input.getIds() == null;
        if (!StringUtils.hasText(assetIds)) {
            if (!idsInputIsEmpty) {
                assetIds = input.getIds();
            } else {
                assetIds = null;
            }
        }

        input.setIds(assetIds);

        // List the games
        return Output.builder()
                .games(gameDataProvider.listByCriteria(input))
                .build();
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class Input {
        private String jwt;
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
