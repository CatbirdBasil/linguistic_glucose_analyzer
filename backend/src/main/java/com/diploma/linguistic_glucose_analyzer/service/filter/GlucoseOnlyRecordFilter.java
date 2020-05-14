package com.diploma.linguistic_glucose_analyzer.service.filter;

import com.diploma.linguistic_glucose_analyzer.model.GlucoseDataRecord;
import com.diploma.linguistic_glucose_analyzer.utils.GlucoseDataCodeUtils;

import java.util.List;
import java.util.stream.Collectors;

public class GlucoseOnlyRecordFilter implements RecordFilter {
    @Override
    public List<GlucoseDataRecord> filter(List<GlucoseDataRecord> records) {
        return records.stream()
                .filter(rec -> GlucoseDataCodeUtils.isGlucoseMeasurement(rec.getCode()))
                .collect(Collectors.toList());
    }
}
