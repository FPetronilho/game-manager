package com.tracktainment.gamemanager.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public enum OrderDirection {

    ASC("ascending"),
    DESC("descending");

    private final String value;
}
