package com.diploma.linguistic_glucose_analyzer.service;

import com.diploma.linguistic_glucose_analyzer.dto.SymbolBounds;
import com.diploma.linguistic_glucose_analyzer.model.Alphabet;
import com.diploma.linguistic_glucose_analyzer.model.GlucoseDataRecord;
import com.diploma.linguistic_glucose_analyzer.service.filter.RecordFilter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface LinguisticChainService {
    String getChain(List<GlucoseDataRecord> records);

    String getChain(List<GlucoseDataRecord> records, Alphabet alphabet);

    boolean isHypoglycemic(char symbol);

    boolean isHypoglycemic(char symbol, Alphabet alphabet);

    boolean isHyperglycemic(char symbol);

    boolean isHyperglycemic(char symbol, Alphabet alphabet);

    SymbolBounds getSymbolBounds(char symbol);

    SymbolBounds getSymbolBounds(char symbol, Alphabet alphabet);

    //TODO Remove
    Map<Character, Double> getSymbolsUpperBound();
}
