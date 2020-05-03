package com.diploma.linguistic_glucose_analyzer.service;

import com.diploma.linguistic_glucose_analyzer.dao.GlucoseFileDAO;
import com.diploma.linguistic_glucose_analyzer.model.GlucoseDataCode;
import com.diploma.linguistic_glucose_analyzer.model.GlucoseDataRecord;
import com.diploma.linguistic_glucose_analyzer.model.Prediction;
import com.diploma.linguistic_glucose_analyzer.service.filter.RecordFilter;
import com.diploma.linguistic_glucose_analyzer.service.filter.provider.FiltersProvider;
import com.diploma.linguistic_glucose_analyzer.service.matrix.PredictionMatrixFactory;
import com.diploma.linguistic_glucose_analyzer.service.matrix.model.PredictionMatrix;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.diploma.linguistic_glucose_analyzer.constants.LinguisticChainConstants.USED_ALPHABET;

@Slf4j
@Service
public class TestService {
    private GlucoseService glucoseService;
    private PredictionService predictionService;
    private LinguisticChainService linguisticChainService;
    private BadGlucoseFinderService badGlucoseFinderService;

    private GlucoseFileDAO glucoseFileDAO;

    private FiltersProvider filterProvider;
    private PredictionMatrixFactory predictionMatrixFactory;

    private static final String fileBaseName = "Diabetes-Data/data-";

    @Autowired
    public TestService(GlucoseService glucoseService, PredictionService predictionService, LinguisticChainService linguisticChainService, BadGlucoseFinderService badGlucoseFinderService, GlucoseFileDAO glucoseFileDAO, FiltersProvider filterProvider, PredictionMatrixFactory predictionMatrixFactory) {
        this.glucoseService = glucoseService;
        this.predictionService = predictionService;
        this.linguisticChainService = linguisticChainService;
        this.badGlucoseFinderService = badGlucoseFinderService;
        this.glucoseFileDAO = glucoseFileDAO;
        this.filterProvider = filterProvider;
        this.predictionMatrixFactory = predictionMatrixFactory;
    }

    public void test(int predictionLength, int bufferLength) {
        List<Prediction> badGlucosePredictions = new ArrayList<>();
        List<GlucoseDataRecord> allFilteredGlucoseRecords = new ArrayList<>();

        int lessThanPersons = 0;

        for (int i = 1; i <= 60; i++) {
//        for (int i = 32; i <= 34; i++) {
            List<GlucoseDataRecord> records = glucoseFileDAO.getRecords(fileBaseName + getFileNumber(i));

            for (RecordFilter filter : filterProvider.getFilters()) {
                records = filter.filter(records);
            }

            for (GlucoseDataRecord record : records) {
                record.setPersonId(i);
                if (GlucoseDataCode.HYPOGLYCEMIC_SYMPTOMS.equals(record.getCode())) {
                    log.debug("HYPO: Person #{}", i);
                }
            }

            allFilteredGlucoseRecords.addAll(records);

            String glucoseMeasuresChain = linguisticChainService.getChain(records, USED_ALPHABET);

            if ("".equals(glucoseMeasuresChain)) {
//                log.debug("LESS THEN 4 MES/DAY for {} file", i);
                lessThanPersons++;
            } else {
//                log.debug(glucoseMeasuresChain);
                badGlucosePredictions.addAll(badGlucoseFinderService
                        .findBadGlucose(glucoseMeasuresChain, predictionLength, bufferLength, i));
            }
        }
        log.debug("Less then persons: {}", lessThanPersons);

//        double matchRate = 0;
//
//        for (int i = 61; i <= 70; i++) {
//            List<GlucoseDataRecord> records = glucoseFileDAO.getRecords(fileBaseName + getFileNumber(i));
//            String glucoseMeasuresChain = linguisticChainService.getChain(records, USED_ALPHABET);
//            List<Prediction> predictions = predictionService.checkPrediction(glucoseMeasuresChain, badGlucosePredictions);
//            matchRate += calculateMatchRate(glucoseMeasuresChain, predictionLength, bufferLength, predictions);
//        }
//
//        log.debug("Average match rate: {}%", (matchRate / 10) * 100);
        log.debug("LEAST TEN: ");
        seeLeastTen(allFilteredGlucoseRecords);
        log.debug("TOP TEN: ");
        seeTopTen(allFilteredGlucoseRecords);

        PredictionMatrix predictionMatrix = predictionMatrixFactory.getMatrix(allFilteredGlucoseRecords, 3);
//        log.debug("LEAST TEN CHANCES (AB)");
//        log.debug("Possible chains before A: {}", sortDesc(predictionMatrix.getPossibleChainsBeforeSymbol('A')));
//        log.debug("Possible chains before B {}", sortDesc(predictionMatrix.getPossibleChainsBeforeSymbol('B')));
//        log.debug("TOP TEN CHANCES (MNOPQRSTUVWXZ)");
//        log.debug("Possible chains before M: {}", sortDesc(predictionMatrix.getPossibleChainsBeforeSymbol('M')));
//        log.debug("Possible chains before O: {}", sortDesc(predictionMatrix.getPossibleChainsBeforeSymbol('O')));
//        log.debug("Possible chains before P: {}", sortDesc(predictionMatrix.getPossibleChainsBeforeSymbol('P')));
//        log.debug("Possible chains before Q: {}", sortDesc(predictionMatrix.getPossibleChainsBeforeSymbol('Q')));
//        log.debug("Possible chains before R: {}", sortDesc(predictionMatrix.getPossibleChainsBeforeSymbol('R')));
//        log.debug("Possible chains before S: {}", sortDesc(predictionMatrix.getPossibleChainsBeforeSymbol('S')));
//        log.debug("Possible chains before T: {}", sortDesc(predictionMatrix.getPossibleChainsBeforeSymbol('T')));
//        log.debug("Possible chains before U: {}", sortDesc(predictionMatrix.getPossibleChainsBeforeSymbol('U')));
//        log.debug("Possible chains before V: {}", sortDesc(predictionMatrix.getPossibleChainsBeforeSymbol('V')));
//        log.debug("Possible chains before W: {}", sortDesc(predictionMatrix.getPossibleChainsBeforeSymbol('W')));
//        log.debug("Possible chains before X: {}", sortDesc(predictionMatrix.getPossibleChainsBeforeSymbol('X')));
//        log.debug("Possible chains before Z: {}", sortDesc(predictionMatrix.getPossibleChainsBeforeSymbol('Z')));

        log.debug("LEAST TEN (A)");
        log.debug("Possible chains before A: {}", sortDesc(predictionMatrix.getPossibleChainsBeforeSymbol('A')));
        log.debug("TOP TEN (LMNOPQRS)");
        log.debug("Possible chains before L: {}", sortDesc(predictionMatrix.getPossibleChainsBeforeSymbol('L')));
        log.debug("Possible chains before M: {}", sortDesc(predictionMatrix.getPossibleChainsBeforeSymbol('M')));
        log.debug("Possible chains before N: {}", sortDesc(predictionMatrix.getPossibleChainsBeforeSymbol('N')));
        log.debug("Possible chains before O: {}", sortDesc(predictionMatrix.getPossibleChainsBeforeSymbol('O')));
        log.debug("Possible chains before P: {}", sortDesc(predictionMatrix.getPossibleChainsBeforeSymbol('P')));
        log.debug("Possible chains before Q: {}", sortDesc(predictionMatrix.getPossibleChainsBeforeSymbol('Q')));
        log.debug("Possible chains before R: {}", sortDesc(predictionMatrix.getPossibleChainsBeforeSymbol('R')));
        log.debug("Possible chains before S: {}", sortDesc(predictionMatrix.getPossibleChainsBeforeSymbol('S')));

//        log.debug("Prediction matrix: {}", predictionMatrix);
//        log.debug("Possible chains before G: {}", predictionMatrix.getChainOccasions());
    }

//    public void testEChain() {
//        List<GlucoseDataRecord> records = glucoseFileDAO.getRecords(fileBaseName + getFileNumber(1));
//        String glucoseMeasuresChain = linguisticChainService.getChain(records, USED_ALPHABET);
////        log.debug(glucoseMeasuresChain);
////        for (GlucoseDataRecord record: glucoseMeasures) {
////            System.out.println(record.getValue());
////        }
//    }

    private String getFileNumber(int num) {
        return num < 10 ? "0" + num : "" + num;
    }

    private double calculateMatchRate(String linguisticChain,
                                      int predictionLength,
                                      int predictionBuffer,
                                      List<Prediction> foundPredictions) {
        List<Prediction> actualPredictions =
                badGlucoseFinderService.findBadGlucose(linguisticChain, predictionLength, predictionBuffer, 0);
        log.debug("Found predictions = {}, actual = {}", foundPredictions.size(), actualPredictions.size());
        return (double) foundPredictions.size() / actualPredictions.size();
    }

    private void seeLeastTen(List<GlucoseDataRecord> records) {
        List<GlucoseDataRecord> leastTen = records
                .stream()
                .sorted(Comparator.comparingInt(GlucoseDataRecord::getValue))
                .limit(records.size() / 10)
                .collect(Collectors.toList());

        String chain = linguisticChainService.getChain(leastTen);
        chain.chars().mapToObj(c -> Character.toString((char) c))
                .distinct()
                .forEach(System.out::print);
        System.out.println("");
    }

    private void seeTopTen(List<GlucoseDataRecord> records) {
        List<GlucoseDataRecord> leastTen = records
                .stream()
                .sorted(Comparator.comparingInt(GlucoseDataRecord::getValue))
                .skip(records.size() - records.size() / 10)
                .collect(Collectors.toList());

        String chain = linguisticChainService.getChain(leastTen);
        chain.chars().mapToObj(c -> Character.toString((char) c))
                .distinct()
                .forEach(System.out::print);
        System.out.println("");
    }

    private Map<String, Double> sortDesc(Map<String, Double> map) {
//        Map<String, Double> sortedMap = new TreeMap<>((x, y) -> Double.compare(map.get(y), map.get(x)));
//        sortedMap.putAll(map);
//        return sortedMap;

//        return map
//                .entrySet()
//                .stream()
//                .sorted((x, y) -> Double.compare(y.getValue(), x.getValue()))
//                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2));

        Map<String, Double> sortedMap = new LinkedHashMap<>();

        map
                .entrySet()
                .stream()
                .sorted((x, y) -> Double.compare(y.getValue(), x.getValue()))
                .forEachOrdered(entry ->
                        sortedMap.put(entry.getKey(), entry.getValue()));

        return sortedMap;
    }
}

