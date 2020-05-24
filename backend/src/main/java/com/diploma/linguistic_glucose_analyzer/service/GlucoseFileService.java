package com.diploma.linguistic_glucose_analyzer.service;

import com.diploma.linguistic_glucose_analyzer.model.GlucoseDataRecord;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.List;

public interface GlucoseFileService {
    List<GlucoseDataRecord> getRecords(String filePath);
    List<GlucoseDataRecord> getRecords(File file);
    List<GlucoseDataRecord> getRecords(String[] rows);
}
