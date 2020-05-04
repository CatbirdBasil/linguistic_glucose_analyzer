package com.diploma.linguistic_glucose_analyzer.constants;

import com.diploma.linguistic_glucose_analyzer.model.Alphabet;

public interface LinguisticChainConstants {
    int MIN_GLUCOSE = 0;
    int MAX_GLUCOSE = 520;

    int GLUCOSE_MIN_HEALTHY = 40;
    int GLUCOSE_MAX_HEALTHY = 200;

    int MIN_MEASURES_PER_DAY_PER_PERSON = 2;

    Alphabet USED_ALPHABET = Alphabet.ASCII100;

    String DEFAULT_ZONE = "America/Toronto";
}
