package com.diploma.linguistic_glucose_analyzer.service;

import com.diploma.linguistic_glucose_analyzer.dao.GlucoseFileDAO;
import com.diploma.linguistic_glucose_analyzer.model.GlucoseDataRecord;
import com.diploma.linguistic_glucose_analyzer.model.Prediction;
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

    private static final String fileBaseName = "Diabetes-Data/data-";

    @Autowired
    public TestService(GlucoseService glucoseService, PredictionService predictionService, LinguisticChainService linguisticChainService, BadGlucoseFinderService badGlucoseFinderService, GlucoseFileDAO glucoseFileDAO) {
        this.glucoseService = glucoseService;
        this.predictionService = predictionService;
        this.linguisticChainService = linguisticChainService;
        this.badGlucoseFinderService = badGlucoseFinderService;
        this.glucoseFileDAO = glucoseFileDAO;
    }

    //TODO make critacl pvalues finder
    public void test(int predictionLength, int bufferLength) {
        List<Prediction> badGlucosePredictions = new ArrayList<>();

        for (int i = 1; i <= 60; i++) {
            List<GlucoseDataRecord> records = glucoseFileDAO.getRecords(fileBaseName + getFileNumber(i));
            List<GlucoseDataRecord> glucoseMeasures = glucoseService.getGlucoseMeasures(records);
            String glucoseMeasuresChain = linguisticChainService.getChain(glucoseMeasures, USED_ALPHABET);
            badGlucosePredictions.addAll(badGlucoseFinderService
                    .findBadGlucose(glucoseMeasuresChain, predictionLength, bufferLength, i));
        }

        double matchRate = 0;

        for (int i = 61; i <= 70; i++) {
//        for (int i = 21; i <= 50; i++) {
            List<GlucoseDataRecord> records = glucoseFileDAO.getRecords(fileBaseName + getFileNumber(i));
            List<GlucoseDataRecord> glucoseMeasures = glucoseService.getGlucoseMeasures(records);
            String glucoseMeasuresChain = linguisticChainService.getChain(glucoseMeasures, USED_ALPHABET);
            List<Prediction> predictions = predictionService.checkPrediction(glucoseMeasuresChain, badGlucosePredictions);
            matchRate += calculateMatchRate(glucoseMeasuresChain, predictionLength, bufferLength, predictions);
        }

        log.debug("Average match rate: {}%", (matchRate / 10) * 100);
    }

    public void testChain() {
        List<GlucoseDataRecord> records = glucoseFileDAO.getRecords(fileBaseName + getFileNumber(1));
        List<GlucoseDataRecord> glucoseMeasures = glucoseService.getGlucoseMeasures(records);
        String glucoseMeasuresChain = linguisticChainService.getChain(glucoseMeasures, USED_ALPHABET);
        log.debug(glucoseMeasuresChain);
        for (GlucoseDataRecord record: glucoseMeasures) {
            System.out.println(record.getValue());
        }
    }

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

