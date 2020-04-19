package com.diploma.linguistic_glucose_analyzer.service;

import com.diploma.linguistic_glucose_analyzer.model.Prediction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PredictionService {
    List<Prediction> checkPrediction(String linguisticChain);
    List<Prediction> checkPrediction(String linguisticChain, int length);

    List<Prediction> checkPrediction(String linguisticChain, List<Prediction> predictions);

    void savePrediction(Prediction prediction);
}
