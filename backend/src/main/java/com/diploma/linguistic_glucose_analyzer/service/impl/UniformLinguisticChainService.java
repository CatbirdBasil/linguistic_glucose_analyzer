package com.diploma.linguistic_glucose_analyzer.service.impl;

import com.diploma.linguistic_glucose_analyzer.constants.LinguisticChainConstants;
import com.diploma.linguistic_glucose_analyzer.model.Alphabet;
import com.diploma.linguistic_glucose_analyzer.model.GlucoseDataRecord;
import com.diploma.linguistic_glucose_analyzer.service.LinguisticChainService;
import com.diploma.linguistic_glucose_analyzer.service.impl.AbstractLinguisticChainService;
import com.diploma.linguistic_glucose_analyzer.utils.GlucoseDataCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static com.diploma.linguistic_glucose_analyzer.constants.LinguisticChainConstants.*;

@Slf4j
//@Primary
@Service
public class UniformLinguisticChainService extends AbstractLinguisticChainService implements LinguisticChainService {

    @Override
    protected Function<Double, Double> getDistributionFunc() {
        return x -> 1.0;
    }

    @Override
    protected double getStartFrom() {
        return 0;
    }

    @Override
    protected double getFinishAt() {
        return 100;
    }
}
