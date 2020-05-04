package com.diploma.linguistic_glucose_analyzer.service.impl;

import com.diploma.linguistic_glucose_analyzer.constants.LinguisticChainConstants;
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
    public String getChain(List<GlucoseDataRecord> records) {
        return getChain(records, USED_ALPHABET);
    }


    @Override
    public String getChain(List<GlucoseDataRecord> records, Alphabet alphabet) {
        double step = getStep(alphabet);
        char[] symbols = alphabet.getSymbols();

        StringBuilder linguisticChain = new StringBuilder();
        for (GlucoseDataRecord record : records) {
            char symbolToAppend = record.getValue() > MAX_GLUCOSE ?
                    symbols[symbols.length - 1] :
                    symbols[(int) Math.floor((record.getValue() - MIN_GLUCOSE) / step)];

            linguisticChain.append(symbolToAppend);
        }

        return linguisticChain.toString();
    }

    @Override
    public boolean isHyperglycemic(char symbol) {
        return isHyperglycemic(symbol, USED_ALPHABET);
    }

    @Override
    public boolean isHyperglycemic(char symbol, Alphabet alphabet) {
        var symbolIndex = Arrays.binarySearch(alphabet.getSymbols(), symbol);

        return symbolIndex != -1 && (symbolIndex + 1) * getStep(alphabet) > GLUCOSE_MAX_HEALTHY;
    }

    @Override
    public boolean isHypoglycemic(char symbol) {
        return isHypoglycemic(symbol, USED_ALPHABET);
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
