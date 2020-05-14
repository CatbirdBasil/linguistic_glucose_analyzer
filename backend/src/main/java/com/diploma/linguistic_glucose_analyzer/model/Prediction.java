package com.diploma.linguistic_glucose_analyzer.model;

import com.diploma.linguistic_glucose_analyzer.model.ProblemType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Prediction {
    private String linguisticChain;
    private String possibleResult;
    private ProblemType type;
    private long personId;
}
