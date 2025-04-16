package com.tracktainment.gamemanager.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
@Schema(description = "Field sorting options")
public enum OrderBy {

    @Schema(description = "Sort by game title")
    TITLE("title"),

    @Schema(description = "Sort by platform")
    PLATFORM("platform"),

    @Schema(description = "Sort by genre")
    GENRE("genre"),

    @Schema(description = "Sort by creation date")
    CREATED_AT("createdAt");

    private final String value;
}
