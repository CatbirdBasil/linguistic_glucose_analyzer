package com.diploma.linguistic_glucose_analyzer.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
public class GlucoseDataRecord {
    private Instant eventTime;
    private GlucoseDataCode code;
    private int value;
    private long personId;

    public GlucoseDataRecord(Instant eventTime, GlucoseDataCode code, int value) {
        this.eventTime = eventTime;
        this.code = code;
        this.value = value;
    }
}
