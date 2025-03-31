package com.tracktainment.gamemanager.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public enum OrderBy {

    TITLE("title"),
    PLATFORM("platform"),
    GENRE("genre"),
    CREATED_AT("createdAt");

    private final String value;
}
