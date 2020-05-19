package com.diploma.linguistic_glucose_analyzer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SymbolBounds {
    private char symbol;
    private double min;
    private double max;
}
