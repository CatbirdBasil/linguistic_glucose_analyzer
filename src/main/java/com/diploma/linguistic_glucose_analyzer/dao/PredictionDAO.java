package com.diploma.linguistic_glucose_analyzer.dao;

import com.diploma.linguistic_glucose_analyzer.model.Prediction;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PredictionDAO {
    List<Prediction> getAllPredictions();
    List<Prediction> getPredictionsOfLength(int length);

    void savePrediction(Prediction prediction);
}
