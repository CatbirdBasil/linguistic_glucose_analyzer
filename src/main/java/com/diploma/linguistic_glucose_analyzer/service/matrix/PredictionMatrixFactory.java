package com.diploma.linguistic_glucose_analyzer.service.matrix;

import com.diploma.linguistic_glucose_analyzer.model.GlucoseDataRecord;
import com.diploma.linguistic_glucose_analyzer.service.matrix.model.PredictionMatrix;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PredictionMatrixFactory {
    PredictionMatrix getMatrix(List<GlucoseDataRecord> records, int chainLength);
}
