package com.diploma.linguistic_glucose_analyzer.model;

import lombok.Getter;

@Getter
public enum Alphabet {
    ENGLISH(1L, "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()),
    ASCII100(2L, Alphabet.generateCharArray(26, 126));

    private final long id;
    private final char[] symbols;

    Alphabet(long id, char[] symbols) {
        this.id = id;
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

    public static Alphabet valueOf(long id) {
        for (Alphabet alphabet : values()) {
            if (alphabet.id == id) {
                return alphabet;
            }
        }

        return null;
    }
}
