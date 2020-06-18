package com.diploma.linguistic_glucose_analyzer.service;

import com.diploma.linguistic_glucose_analyzer.dto.PossibleFutureGlucoseDTO;

public interface PredictionService {
    PossibleFutureGlucoseDTO getPossibleFutureGlucoseValueForUser(long userId);
    String getPossibleFutureGlucoseValueForUserQualityTest(long userId);
}
