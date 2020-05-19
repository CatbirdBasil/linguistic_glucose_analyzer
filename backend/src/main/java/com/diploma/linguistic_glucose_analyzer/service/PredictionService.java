package com.diploma.linguistic_glucose_analyzer.service;

import com.diploma.linguistic_glucose_analyzer.dto.PossibleFutureGlucoseDTO;
import com.diploma.linguistic_glucose_analyzer.model.Prediction;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PredictionService {
    PossibleFutureGlucoseDTO getPossibleFutureGlucoseValueForUser(long userId);
}
