package com.diploma.linguistic_glucose_analyzer.service.impl;

import com.diploma.linguistic_glucose_analyzer.service.LinguisticChainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Slf4j
@Primary
@Service
public class ParabolicLinguisticChainService extends AbstractLinguisticChainService implements LinguisticChainService {

//    private final static Double POWER = 2.0;
//    private final static Double FOLD = 1.0;
//
//    private final static Double X_OFFSET = 1.0;
//    private final static Double Y_OFFSET = 0.3;
//
//    private final static Double START_FROM = 0.0;
//    private final static Double FINISH_AT = 2.5;

    private final static Double POWER = 4.0;
    private final static Double FOLD = 5.0;

    private final static Double X_OFFSET = 0.7;
    private final static Double Y_OFFSET = 0.3;

    private final static Double START_FROM = 0.0;
    private final static Double FINISH_AT = 1.7;

    @Override
    protected Function<Double, Double> getDistributionFunc() {
        return x -> FOLD * Math.pow(x - X_OFFSET, POWER) + Y_OFFSET;
    }

    @Override
    protected double getStartFrom() {
        return START_FROM;
    }

    @Override
    protected double getFinishAt() {
        return FINISH_AT;
    }
}