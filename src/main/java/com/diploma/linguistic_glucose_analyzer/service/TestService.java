package com.diploma.linguistic_glucose_analyzer.service;

import com.diploma.linguistic_glucose_analyzer.dao.GlucoseFileDAO;
import com.diploma.linguistic_glucose_analyzer.model.GlucoseDataRecord;
import com.diploma.linguistic_glucose_analyzer.model.Prediction;
import com.diploma.linguistic_glucose_analyzer.service.filter.RecordFilter;
import com.diploma.linguistic_glucose_analyzer.service.filter.provider.FiltersProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    private static final String fileBaseName = "Diabetes-Data/data-";

    @Autowired
    public TestService(GlucoseService glucoseService, PredictionService predictionService, LinguisticChainService linguisticChainService, BadGlucoseFinderService badGlucoseFinderService, GlucoseFileDAO glucoseFileDAO, FiltersProvider filterProvider) {
        this.glucoseService = glucoseService;
        this.predictionService = predictionService;
        this.linguisticChainService = linguisticChainService;
        this.badGlucoseFinderService = badGlucoseFinderService;
        this.glucoseFileDAO = glucoseFileDAO;
        this.filterProvider = filterProvider;
    }

    public void test(int predictionLength, int bufferLength) {
        List<Prediction> badGlucosePredictions = new ArrayList<>();

        int lessThanPersons = 0;

        for (int i = 1; i <= 60; i++) {
            List<GlucoseDataRecord> records = glucoseFileDAO.getRecords(fileBaseName + getFileNumber(i));

            for (RecordFilter filter : filterProvider.getFilters()) {
                records = filter.filter(records);
            }

            String glucoseMeasuresChain = linguisticChainService.getChain(records, USED_ALPHABET);

            if ("".equals(glucoseMeasuresChain)) {
                log.debug("LESS THEN 4 MES/DAY for {} file", i);
                lessThanPersons++;
            } else {
                badGlucosePredictions.addAll(badGlucoseFinderService
                        .findBadGlucose(glucoseMeasuresChain, predictionLength, bufferLength, i));
            }
        }
        log.debug("Less then persons: {}", lessThanPersons);

        double matchRate = 0;

        for (int i = 61; i <= 70; i++) {
//        for (int i = 21; i <= 50; i++) {
            List<GlucoseDataRecord> records = glucoseFileDAO.getRecords(fileBaseName + getFileNumber(i));
            String glucoseMeasuresChain = linguisticChainService.getChain(records, USED_ALPHABET);
            List<Prediction> predictions = predictionService.checkPrediction(glucoseMeasuresChain, badGlucosePredictions);
            matchRate += calculateMatchRate(glucoseMeasuresChain, predictionLength, bufferLength, predictions);
        }

        log.debug("Average match rate: {}%", (matchRate / 10) * 100);
    }

//    public void testChain() {
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

}

