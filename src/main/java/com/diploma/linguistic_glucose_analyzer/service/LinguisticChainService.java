package com.diploma.linguistic_glucose_analyzer.service;

import com.diploma.linguistic_glucose_analyzer.model.Alphabet;
import com.diploma.linguistic_glucose_analyzer.model.GlucoseDataRecord;
import com.diploma.linguistic_glucose_analyzer.service.filter.RecordFilter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LinguisticChainService {
    String getChain(List<GlucoseDataRecord> records, Alphabet alphabet);
    boolean isHypoglycemic(char symbol, Alphabet alphabet);
    boolean isHyperglycemic(char symbol, Alphabet alphabet);
}
