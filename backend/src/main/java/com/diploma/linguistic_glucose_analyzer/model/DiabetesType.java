package com.diploma.linguistic_glucose_analyzer.model;

import lombok.Getter;

@Getter
public enum DiabetesType {
    TYPE_ONE(1L),
    TYPE_TWO(2L),
    NO_DIABETES(3L);

    private final long id;

    DiabetesType(long id) {
        this.id = id;
    }

    public static DiabetesType valueOf(long id) {
        for (DiabetesType type : values()) {
            if (type.id == id) {
                return type;
            }
        }

        return null;
    }
}
