package com.diploma.linguistic_glucose_analyzer.model;

import lombok.Data;

@Data
public class PredictionChainOccasion {
    private long id;
    private long alphabetId;
    private Alphabet alphabet;
    private long distributionId;
    private Distribution distribution;
    private long personId;
    private Person person;
    private String linguisticChain;
    private String possibleResult;
    private long occasions;
}
