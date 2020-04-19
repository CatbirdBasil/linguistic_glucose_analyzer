package com.diploma.linguistic_glucose_analyzer.service.impl;

import com.diploma.linguistic_glucose_analyzer.constants.LinguisticChainConstants;
import com.diploma.linguistic_glucose_analyzer.model.Prediction;
import com.diploma.linguistic_glucose_analyzer.model.ProblemType;
import com.diploma.linguistic_glucose_analyzer.service.BadGlucoseFinderService;
import com.diploma.linguistic_glucose_analyzer.service.LinguisticChainService;
import com.diploma.linguistic_glucose_analyzer.service.PredictionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class BadGlucoseFinderServiceImpl implements BadGlucoseFinderService {
    private LinguisticChainService linguisticChainService;

    @Autowired
    public BadGlucoseFinderServiceImpl(LinguisticChainService linguisticChainService) {
        this.linguisticChainService = linguisticChainService;
    }

    /**
     * Works properly only if linguistic chain with glucose measurements is passed
     * @param linguisticChain
     * @param chainLength
     * @param predictionBuffer
     * @param personId
     * @return
     */
    @Override
    public List<Prediction> findBadGlucose(String linguisticChain, int chainLength, int predictionBuffer, int personId) {
        List<Prediction> predictions = new ArrayList<>();

        int wholePrediction = chainLength + predictionBuffer;

        for (int i = wholePrediction - 1; i < linguisticChain.length(); i++) {
            char symbol = linguisticChain.charAt(i);

            ProblemType type = null;
            if (linguisticChainService.isHypoglycemic(symbol, LinguisticChainConstants.USED_ALPHABET)) {
                type = ProblemType.HYPOGLYCEMIA;
            } else if (linguisticChainService.isHyperglycemic(symbol, LinguisticChainConstants.USED_ALPHABET)) {
                type = ProblemType.HYPERGLYCEMIA;
            }

            if (type != null) {
                int startPoint = i - wholePrediction + 1;
                String chain = linguisticChain.substring(startPoint, startPoint + chainLength);
                String buffer = linguisticChain.substring(startPoint + chainLength, startPoint + wholePrediction);
                predictions.add(new Prediction(chain, buffer, type, personId));
            }
        }

        return predictions;
    }
}
