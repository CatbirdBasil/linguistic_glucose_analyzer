package com.diploma.linguistic_glucose_analyzer.service.impl;

import com.diploma.linguistic_glucose_analyzer.model.Alphabet;
import com.diploma.linguistic_glucose_analyzer.model.GlucoseDataRecord;
import com.diploma.linguistic_glucose_analyzer.service.LinguisticChainService;
import com.diploma.linguistic_glucose_analyzer.utils.GlucoseDataCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static com.diploma.linguistic_glucose_analyzer.constants.LinguisticChainConstants.*;

@Slf4j
@Service
public class LinguisticChainServiceImpl implements LinguisticChainService {
    @Override
    public String getChain(List<GlucoseDataRecord> records, Alphabet alphabet) {
        double step = getStep(alphabet);

        StringBuilder linguisticChain = new StringBuilder();
        for (GlucoseDataRecord record : records) {
            if (GlucoseDataCodeUtils.isGlucoseMeasurement(record.getCode())) {
                int letterIndex = (int) Math.floor((record.getValue() - MIN_GLUCOSE) / step);
                if (letterIndex == 26) {
                    log.debug("Record = {}", record);
                }
                linguisticChain.append(alphabet.getSymbols()[letterIndex]);
            }
        }

        return linguisticChain.toString();
    }

    @Override
    public boolean isHyperglycemic(char symbol, Alphabet alphabet) {
        var symbolIndex = Arrays.binarySearch(alphabet.getSymbols(), symbol);

        return symbolIndex != -1 && (symbolIndex + 1) * getStep(alphabet) > GLUCOSE_MAX_HEALTHY;
    }

    @Override
    public boolean isHypoglycemic(char symbol, Alphabet alphabet) {
        var symbolIndex = Arrays.binarySearch(alphabet.getSymbols(), symbol);

        return symbolIndex != -1 && (symbolIndex) * getStep(alphabet) < GLUCOSE_MIN_HEALTHY;
    }

    private double getStep(Alphabet alphabet) {
        return (double) (MAX_GLUCOSE - MIN_GLUCOSE) / alphabet.getSymbols().length;
    }
}
