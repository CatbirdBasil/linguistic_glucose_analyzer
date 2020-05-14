package com.diploma.linguistic_glucose_analyzer.service.matrix.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public class PredictionMatrix {
    private int chainLength;
    private Map<String, Map<Character, Double>> appearanceChances;
    private Map<String, Integer> chainOccasions;
    private Map<String, Map<Character, Integer>> charAfterChainOccasions;

    public double getAppearanceChance(String chain, char expectedSymbol) {
        if (chain == null || appearanceChances == null || chain.length() != chainLength) {
            throw new IllegalArgumentException();
        }

        return appearanceChances.get(chain).get(expectedSymbol);
    }

    public Map<String, Double> getPossibleChainsBeforeSymbol(char symbol) {
        Map<String, Integer> possibleChains = new HashMap<>();

        for (Map.Entry<String, Map<Character, Integer>> occasions : charAfterChainOccasions.entrySet()) {
            String chain = occasions.getKey();
            Integer charOccasions = occasions.getValue().get(symbol);

            if (charOccasions != null) {
                possibleChains.put(chain, charOccasions);
            }
        }

        int overallOccasions = possibleChains
                .values()
                .stream()
                .mapToInt(x -> x)
                .sum();

        Map<String, Double> possibleChainsChances = new HashMap<>();

        for (Map.Entry<String, Integer> possibleChain : possibleChains.entrySet()) {
            possibleChainsChances.put(possibleChain.getKey(), (double) possibleChain.getValue() / overallOccasions);
        }

        return possibleChainsChances;
    }
}
