package com.diploma.linguistic_glucose_analyzer.service.matrix.impl;

import com.diploma.linguistic_glucose_analyzer.model.GlucoseDataRecord;
import com.diploma.linguistic_glucose_analyzer.service.LinguisticChainService;
import com.diploma.linguistic_glucose_analyzer.service.matrix.PredictionMatrixFactory;
import com.diploma.linguistic_glucose_analyzer.service.matrix.model.PredictionMatrix;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PredictionMatrixFactoryImpl implements PredictionMatrixFactory {

    @Autowired
    private LinguisticChainService linguisticChainService;

    @Override
    public PredictionMatrix getMatrix(List<GlucoseDataRecord> records, int chainLength) {
        log.debug("Persons: {}", records.size());

        Collection<List<GlucoseDataRecord>> filteredRecords = records.stream()
                .filter(record -> record.getPersonId() != 0L)
                .collect(Collectors.groupingBy(GlucoseDataRecord::getPersonId))
                .values();

        log.debug("Persons: {}", filteredRecords.size());

        List<String> personChains = filteredRecords.stream()
                .map(personRecords -> linguisticChainService.getChain(personRecords))
                .collect(Collectors.toList());

        log.debug("Person chains: {}", personChains.size());

        Map<String, Integer> chainOccasions = new HashMap<>();
        Map<String, Map<Character, Integer>> charAfterChainOccasions = new HashMap<>();

        for (String personChain : personChains) {
            for (int i = chainLength; i < personChain.length(); i++) {
                String chain = personChain.substring(i - chainLength, i);
                Integer numSeen = chainOccasions.get(chain);

                if (numSeen == null) {
                    numSeen = 0;
                }

                chainOccasions.put(chain, ++numSeen);

                Map<Character, Integer> numCharsSeen = charAfterChainOccasions.get(chain);

                if (numCharsSeen == null) {
                    numCharsSeen = new HashMap<>();
                }

                char currSymbol = personChain.charAt(i);
                Integer numSymbolSeen = numCharsSeen.get(currSymbol);

                if (numSymbolSeen == null) {
                    numSymbolSeen = 0;
                }

                numCharsSeen.put(currSymbol, ++numSymbolSeen);
                charAfterChainOccasions.put(chain, numCharsSeen);
            }
        }

        Map<String, Map<Character, Double>> appearanceChances = new HashMap<>();

        for (Map.Entry<String, Integer> occasions : chainOccasions.entrySet()) {
            Map<Character, Double> currChainChances = new HashMap<>();

            String chain = occasions.getKey();
            int overallChainOccasions = occasions.getValue();

            Map<Character, Integer> charsOccasions = charAfterChainOccasions.get(chain);

            for (Map.Entry<Character, Integer> charOccasions : charsOccasions.entrySet()) {
                currChainChances.put(charOccasions.getKey(), (double) charOccasions.getValue() / overallChainOccasions);
            }

            appearanceChances.put(chain, currChainChances);
        }

        return new PredictionMatrix(chainLength, appearanceChances, chainOccasions, charAfterChainOccasions);
    }
}
