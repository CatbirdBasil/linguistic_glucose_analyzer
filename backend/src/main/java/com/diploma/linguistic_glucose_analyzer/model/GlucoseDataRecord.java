package com.diploma.linguistic_glucose_analyzer.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
public class GlucoseDataRecord {
    private long id;
    private Instant eventTime; //TODO Possible problems with date saving to db
    private GlucoseDataCode code;
    private int value;
    private long personId;

    public GlucoseDataRecord(Instant eventTime, GlucoseDataCode code, int value) {
        this.eventTime = eventTime;
        this.code = code;
        this.value = value;
    }
}
