package com.diploma.linguistic_glucose_analyzer.service.filter;

import com.diploma.linguistic_glucose_analyzer.model.GlucoseDataRecord;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MinMeasuresPerDayRecordFilter implements RecordFilter {
    private int minMeasures;

    public MinMeasuresPerDayRecordFilter(int minMeasures) {
        this.minMeasures = minMeasures;
    }

    @Override
    public List<GlucoseDataRecord> filter(List<GlucoseDataRecord> records) {
        List<Instant> eventTimes = records.stream()
                .map(GlucoseDataRecord::getEventTime)
                .collect(Collectors.toList());

        return eventTimes.stream()
                .filter(i -> Collections.frequency(eventTimes, i) >= minMeasures)
                .count() == records.size()
                ? records
                : new ArrayList<>();
    }
}
