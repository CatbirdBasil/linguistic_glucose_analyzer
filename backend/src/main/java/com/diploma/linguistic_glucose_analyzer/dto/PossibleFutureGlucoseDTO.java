package com.diploma.linguistic_glucose_analyzer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class PossibleFutureGlucoseDTO {
    private Instant approximateTime;
    private Double min;
    private Double max;
}
