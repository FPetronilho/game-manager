package com.tracktainment.gamemanager.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class Game {

    private String id;
    private String title;
    private String platform;
    private String genre;
    private String developer;
    private LocalDate releaseDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
