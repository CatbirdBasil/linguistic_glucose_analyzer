package com.diploma.linguistic_glucose_analyzer.model;

import lombok.Getter;

@Getter
public enum Alphabet {
    ENGLISH("ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()),
    ASCII100(Alphabet.generateCharArray(26, 126));

    private final char[] symbols;

    Alphabet(char[] symbols) {
        this.symbols = symbols;
    }

    public static char[] generateCharArray(int start, int end) {
        int size = end - start;

        char[] result = new char[size];

        for (int i = 0; i < size; i++) {
            result[i] = (char) (i + start);
        }

        return result;
    }
}
