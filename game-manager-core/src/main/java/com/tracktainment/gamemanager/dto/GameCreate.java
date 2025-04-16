package com.tracktainment.gamemanager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tracktainment.gamemanager.util.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Data for creating a new game")
public class GameCreate {

    @NotNull(message = Constants.TITLE_MANDATORY_MSG)
    @Pattern(regexp = Constants.TITLE_REGEX, message = Constants.TITLE_INVALID_MSG)
    @Schema(description = "Title of the game", example = "The Last of Us Part II")
    private String title;

    @NotNull(message = Constants.PLATFORM_MANDATORY_MSG)
    @Pattern(regexp = Constants.PLATFORM_REGEX, message = Constants.PLATFORM_INVALID_MSG)
    @Schema(description = "Platform the game is available on", example = "PlayStation 5")
    private String platform;

    @Pattern(regexp = Constants.GENRE_REGEX, message = Constants.GENRE_INVALID_MSG)
    @Schema(description = "Genre of the game", example = "Action")
    private String genre;

    @Pattern(regexp = Constants.DEVELOPER_REGEX, message = Constants.DEVELOPER_INVALID_MSG)
    @Schema(description = "Developer of the game", example = "Naughty Dog")
    private String developer;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "Release date of the game", example = "2020-06-19")
    private LocalDate releaseDate;
}
