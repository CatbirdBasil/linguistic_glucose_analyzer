package com.diploma.linguistic_glucose_analyzer.dao;

import com.diploma.linguistic_glucose_analyzer.model.GlucoseDataRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GlucoseDAO {
    List<GlucoseDataRecord> getAllRecords();
    List<GlucoseDataRecord> getRecordsByPerson(long personId);

    void saveRecordsForPerson(List<GlucoseDataRecord> records, long personId);
}
