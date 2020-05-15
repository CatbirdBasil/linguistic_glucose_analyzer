package com.diploma.linguistic_glucose_analyzer.model;

import lombok.Getter;

@Getter
public enum Distribution {
    UNIFORM(1L),
    LOG_NORMAL(2L),
    PARABOLIC(3L);

    private final long id;

    Distribution(long id) {
        this.id = id;
    }
}
