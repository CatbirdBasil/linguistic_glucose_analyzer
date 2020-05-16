package com.diploma.linguistic_glucose_analyzer.service;

import com.diploma.linguistic_glucose_analyzer.model.GlucoseDataRecord;
import org.springframework.stereotype.Service;

import java.util.List;

public interface GlucoseService extends CrudService<GlucoseDataRecord, Long> {
    List<GlucoseDataRecord> getRecordsByPerson(long personId);
    List<GlucoseDataRecord> getRecordsByUser(long userId);
}
