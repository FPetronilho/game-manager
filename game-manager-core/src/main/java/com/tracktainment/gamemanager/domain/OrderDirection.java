package com.tracktainment.gamemanager.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
@Schema(description = "Sort direction options")
public enum OrderDirection {

    @Schema(description = "Ascending order")
    ASC("ascending"),

    @Schema(description = "Descending order")
    DESC("descending");

    private final String value;
}
