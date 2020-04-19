package com.diploma.linguistic_glucose_analyzer.service.filter;

import com.diploma.linguistic_glucose_analyzer.model.GlucoseDataRecord;

import java.util.List;

public interface RecordFilter {
    List<GlucoseDataRecord> filter(List<GlucoseDataRecord> records);
}
