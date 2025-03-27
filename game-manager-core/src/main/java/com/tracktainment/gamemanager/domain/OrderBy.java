package com.tracktainment.gamemanager.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public enum OrderBy {

    TITLE("title"),
    GENRE("genre"),
    CREATED_AT("created");

    private final String value;
}
