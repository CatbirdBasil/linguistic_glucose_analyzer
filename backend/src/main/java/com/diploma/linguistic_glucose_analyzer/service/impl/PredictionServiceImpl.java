package com.diploma.linguistic_glucose_analyzer.service.impl;

import com.diploma.linguistic_glucose_analyzer.dao.PredictionDAO;
import com.diploma.linguistic_glucose_analyzer.model.Prediction;
import com.diploma.linguistic_glucose_analyzer.service.PredictionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class PredictionServiceImpl implements PredictionService {
    @Autowired
    private PredictionDAO predictionDAO;

    @Override
    public List<Prediction> checkPrediction(String linguisticChain) {
        var predictions = predictionDAO.getAllPredictions();
        return checkPrediction(predictions, linguisticChain);
    }

    @Override
    public List<Prediction> checkPrediction(String linguisticChain, int length) {
        var predictions = predictionDAO.getPredictionsOfLength(length);
        return checkPrediction(predictions, linguisticChain);
    }

    @Override
    public List<Prediction> checkPrediction(String linguisticChain, List<Prediction> predictions) {
        return checkPrediction(predictions, linguisticChain);
    }

    //TODO Improve performance
    private List<Prediction> checkPrediction(List<Prediction> checkedPredictions, String linguisticChain) {
        List<Prediction> matchedPredictions = new ArrayList<>();

        for (Prediction prediction : checkedPredictions) {
            int lengthDifference = linguisticChain.length() - prediction.getLinguisticChain().length();

            if (lengthDifference >= 0) {
                for (int i = 0; i <= lengthDifference; i++) {
                    String checkedPiece = linguisticChain.substring(i, i + prediction.getLinguisticChain().length());
                    if (prediction.getLinguisticChain().equals(checkedPiece)) {
//                        log.trace("Prediction matched: result = {}, chain = {}", prediction.getPossibleResult(), prediction.getLinguisticChain());
                        matchedPredictions.add(prediction);
                    }
                }
            }
        }

        return matchedPredictions;
    }

    @Override
    public void savePrediction(Prediction prediction) {
        predictionDAO.savePrediction(prediction);
    }
}
