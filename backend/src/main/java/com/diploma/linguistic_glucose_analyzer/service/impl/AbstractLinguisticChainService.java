package com.diploma.linguistic_glucose_analyzer.service.impl;

import com.diploma.linguistic_glucose_analyzer.model.Alphabet;
import com.diploma.linguistic_glucose_analyzer.model.GlucoseDataRecord;
import com.diploma.linguistic_glucose_analyzer.service.LinguisticChainService;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.diploma.linguistic_glucose_analyzer.constants.LinguisticChainConstants.*;

public abstract class AbstractLinguisticChainService implements LinguisticChainService {

    /**
     * Distribution function used to determine the distribution of numbers between symbols
     * @return
     */
    protected abstract Function<Double, Double> getDistributionFunc();

    /**
     * Where to start the distribution from distribution function
     * @return
     */
    protected abstract double getStartFrom();

    /**
     * Where to end the distribution from distribution function
     * @return
     */
    protected abstract double getFinishAt();

    private Map<Character, Double> getPerSymbolDistribution(Alphabet alphabet) {
        char[] symbols = alphabet.getSymbols();
        double step = (getFinishAt() - getStartFrom()) / symbols.length;

        Map<Character, Double> funcValues = new LinkedHashMap<>();
        double sum = 0;

        for (int i = 0; i < symbols.length; i++) {
            double value = getDistributionFunc().apply(getStartFrom() + step * i);
            sum += value;
            funcValues.put(symbols[i], value);
        }

        Map<Character, Double> perSymbolDistribution = new LinkedHashMap<>();

        for (char symbol : symbols) {
            perSymbolDistribution.put(symbol, funcValues.get(symbol) / sum);
        }

        return perSymbolDistribution;
    }

    //TODO Make caching for used_alphabet
    private Map<Character, Double> getUpperSymbolBounds(Alphabet alphabet) {
        Map<Character, Double> upperSymbolBounds = new LinkedHashMap<>();

        char[] symbols = alphabet.getSymbols();
        Map<Character, Double> perSymbolDistribution = getPerSymbolDistribution(alphabet);
        double currUpperBound = 0;

        for (char symbol : symbols) {
            currUpperBound += perSymbolDistribution.get(symbol) * (MAX_GLUCOSE - MIN_GLUCOSE);
            upperSymbolBounds.put(symbol, currUpperBound);
        }

        return upperSymbolBounds;
    }

    @Override
    public String getChain(List<GlucoseDataRecord> records) {
        return getChain(records, USED_ALPHABET);
    }


    @Override
    public String getChain(List<GlucoseDataRecord> records, Alphabet alphabet) {
        Map<Character, Double> upperSymbolBounds = getUpperSymbolBounds(alphabet);
        StringBuilder linguisticChain = new StringBuilder();
        for (GlucoseDataRecord record : records) {
            linguisticChain.append(getSymbol(record.getValue(), upperSymbolBounds, alphabet));
        }

        return linguisticChain.toString();
    }

    private char getSymbol(double value, Map<Character, Double> upperSymbolBounds, Alphabet alphabet) {
        if (value < MIN_GLUCOSE || value > MAX_GLUCOSE) {
            throw new IllegalArgumentException();
        }

        for (Map.Entry<Character, Double> upperSymbolBound : upperSymbolBounds.entrySet()) {
            if (value < upperSymbolBound.getValue()) {
                return upperSymbolBound.getKey();
            }
        }

        return alphabet.getSymbols()[alphabet.getSymbols().length - 1];
    }

    @Override
    public boolean isHyperglycemic(char symbol) {
        return isHyperglycemic(symbol, USED_ALPHABET);
    }

    @Override
    public boolean isHyperglycemic(char symbol, Alphabet alphabet) {
        char[] symbols = alphabet.getSymbols();

        if (symbol == symbols[0]) {
            return false;
        }

        return getUpperSymbolBounds(alphabet).get(symbol) > GLUCOSE_MAX_HEALTHY;
    }

    @Override
    public boolean isHypoglycemic(char symbol) {
        return isHypoglycemic(symbol, USED_ALPHABET);
    }

    @Override
    public boolean isHypoglycemic(char symbol, Alphabet alphabet) {
        char[] symbols = alphabet.getSymbols();

        if (symbol == symbols[0]) {
            return true;
        }

        int index =  Arrays.binarySearch(symbols, symbol);

        if (index == -1) {
            return false;
        }

        return getUpperSymbolBounds(alphabet).get(symbols[index - 1]) < GLUCOSE_MIN_HEALTHY;
    }


    //TODO Remove

    @Override
    public Map<Character, Double> getSymbolsUpperBound() {
        return getUpperSymbolBounds(USED_ALPHABET);
    }
}
