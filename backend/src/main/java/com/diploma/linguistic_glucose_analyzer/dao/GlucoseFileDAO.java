package com.diploma.linguistic_glucose_analyzer.dao;

import com.diploma.linguistic_glucose_analyzer.model.GlucoseDataRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface GlucoseFileDAO {
    List<GlucoseDataRecord> getRecords(String filePath);
}
