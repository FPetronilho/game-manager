package com.tracktainment.gamemanager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tracktainment.gamemanager.util.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Data for updating an existing game")
public class GameUpdate {

    @Pattern(regexp = Constants.TITLE_REGEX, message = Constants.TITLE_INVALID_MSG)
    @Schema(description = "New title of the game", example = "The Last of Us Part II: Remastered")
    private String title;

    @Pattern(regexp = Constants.PLATFORM_REGEX, message = Constants.PLATFORM_INVALID_MSG)
    @Schema(description = "New platform the game is available on", example = "PlayStation 5")
    private String platform;

    @Pattern(regexp = Constants.GENRE_REGEX, message = Constants.GENRE_INVALID_MSG)
    @Schema(description = "New genre of the game", example = "Action")
    private String genre;

    @Pattern(regexp = Constants.DEVELOPER_REGEX, message = Constants.DEVELOPER_INVALID_MSG)
    @Schema(description = "New developer of the game", example = "Naughty Dog")
    private String developer;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "New release date of the game", example = "2021-06-19")
    private LocalDate releaseDate;
}
