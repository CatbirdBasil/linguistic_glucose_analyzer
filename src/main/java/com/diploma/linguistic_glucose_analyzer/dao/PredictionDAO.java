package com.diploma.linguistic_glucose_analyzer.dao;

import com.diploma.linguistic_glucose_analyzer.model.Prediction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PredictionDAO {
    List<Prediction> getAllPredictions();
    List<Prediction> getPredictionsOfLength(int length);

    void savePrediction(Prediction prediction);
}
