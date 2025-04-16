package com.tracktainment.gamemanager.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Game information")
public class Game {

    @Schema(description = "Unique identifier of the game", example = "123e4567-e89b-12d3-a456-426614174000")
    private String id;

    @Schema(description = "Title of the game", example = "The Last of Us Part II")
    private String title;

    @Schema(description = "Platform the game is available on", example = "PlayStation 5")
    private String platform;

    @Schema(description = "Genre of the game", example = "Action")
    private String genre;

    @Schema(description = "Developer of the game", example = "Naughty Dog")
    private String developer;

    @Schema(description = "Release date of the game", example = "2020-06-19")
    private LocalDate releaseDate;

    @Schema(description = "Game record creation timestamp", example = "2023-04-15T14:30:45")
    private LocalDateTime createdAt;

    @Schema(description = "Game record last update timestamp", example = "2023-05-20T09:12:33")
    private LocalDateTime updatedAt;
}
