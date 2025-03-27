package com.tracktainment.gamemanager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tracktainment.gamemanager.util.Constants;
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
public class GameCreate {

    @NotNull(message = Constants.TITLE_MANDATORY_MSG)
    @Pattern(regexp = Constants.TITLE_REGEX, message = Constants.TITLE_INVALID_MSG)
    private String title;

    @NotNull(message = Constants.PLATFORM_MANDATORY_MSG)
    @Pattern(regexp = Constants.PLATFORM_REGEX, message = Constants.PLATFORM_INVALID_MSG)
    private String platform;

    @Pattern(regexp = Constants.GENRE_REGEX, message = Constants.GENRE_INVALID_MSG)
    private String genre;

    @Pattern(regexp = Constants.DEVELOPER_REGEX, message = Constants.DEVELOPER_INVALID_MSG)
    private String developer;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate releaseDate;
}
