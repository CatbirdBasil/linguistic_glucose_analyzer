package com.diploma.linguistic_glucose_analyzer.service;

import com.diploma.linguistic_glucose_analyzer.model.GlucoseDataRecord;
import com.diploma.linguistic_glucose_analyzer.model.Prediction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BadGlucoseFinderService {
    //TODO Chain Size & Prediction SIze
    List<Prediction> findBadGlucose(String linguisticChain, int chainLength, int predictionBuffer, int personId);
}
