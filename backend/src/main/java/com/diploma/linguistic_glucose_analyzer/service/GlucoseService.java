package com.diploma.linguistic_glucose_analyzer.service;

import com.diploma.linguistic_glucose_analyzer.model.GlucoseDataRecord;
import org.springframework.stereotype.Service;

import java.util.List;

public interface GlucoseService {
    List<GlucoseDataRecord> getAllRecords();
    List<GlucoseDataRecord> getRecordsByPerson(long personId);

    void saveRecords(List<GlucoseDataRecord> records);
}
