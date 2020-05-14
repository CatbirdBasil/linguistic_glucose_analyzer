package com.diploma.linguistic_glucose_analyzer.dao.impl;

import com.diploma.linguistic_glucose_analyzer.dao.PredictionDAO;
import com.diploma.linguistic_glucose_analyzer.model.Prediction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class ImMemoryPredictionDAO implements PredictionDAO {
    private List<Prediction> predictions;

    public ImMemoryPredictionDAO() {
        predictions = new ArrayList<>();
    }

    @Override
    public List<Prediction> getAllPredictions() {
        log.trace("Getting all predictions");

        return predictions;
    }

    @Override
    public List<Prediction> getPredictionsOfLength(int length) {
        log.trace("Getting predictions of length = {}", length);

        return predictions.stream()
                .filter(prediction -> prediction.getLinguisticChain().length() == length)
                .collect(Collectors.toList());
    }

    @Override
    public void savePrediction(Prediction prediction) {
        log.trace("Saving prediction: {}", prediction);

        predictions.add(prediction);
    }
}
