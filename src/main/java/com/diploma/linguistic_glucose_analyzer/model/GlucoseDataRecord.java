package com.diploma.linguistic_glucose_analyzer.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class GlucoseDataRecord {
    private Instant eventTime;
    private GlucoseDataCode code;
    private int value;
}
