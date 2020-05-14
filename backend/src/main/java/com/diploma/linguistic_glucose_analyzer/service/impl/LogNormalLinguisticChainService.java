package com.diploma.linguistic_glucose_analyzer.service.impl;

import com.diploma.linguistic_glucose_analyzer.model.Alphabet;
import com.diploma.linguistic_glucose_analyzer.model.GlucoseDataRecord;
import com.diploma.linguistic_glucose_analyzer.service.LinguisticChainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.diploma.linguistic_glucose_analyzer.constants.LinguisticChainConstants.*;

@Slf4j
//@Primary
@Service
public class LogNormalLinguisticChainService extends AbstractLinguisticChainService implements LinguisticChainService {

    private final static double o = 0.5;
    private final static double u = 0;

    private final static double START_DISTRIBUTION_FROM = 0.7;
    private final static double FINISH_DISTRIBUTION_AT = 3.3;

    /**
     * Distribution function used to determine the distribution of numbers between symbols
     */
    @Override
    protected Function<Double, Double> getDistributionFunc() {
        return x -> (1 / x * o * Math.sqrt(2 * Math.PI)) * Math.exp(-Math.pow((Math.log(x) - u), 2) / (2 * o * o));
    }

    /**
     * Where to start the distribution from distribution function
     */
    @Override
    protected double getStartFrom() {
        return START_DISTRIBUTION_FROM;
    }

    /**
     * Where to end the distribution from distribution function
     */
    @Override
    protected double getFinishAt() {
        return FINISH_DISTRIBUTION_AT;
    }
}
