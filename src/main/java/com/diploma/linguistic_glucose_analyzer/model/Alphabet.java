package com.diploma.linguistic_glucose_analyzer.model;

import lombok.Getter;

@Getter
public enum Alphabet {
    ENGLISH("ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray());

    private char[] symbols;

    Alphabet(char[] symbols) {
        this.symbols = symbols;
    }
}
