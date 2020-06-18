package com.diploma.linguistic_glucose_analyzer.service.impl;

import com.diploma.linguistic_glucose_analyzer.dto.PossibleFutureGlucoseDTO;
import com.diploma.linguistic_glucose_analyzer.dto.SymbolBounds;
import com.diploma.linguistic_glucose_analyzer.model.DiabetesType;
import com.diploma.linguistic_glucose_analyzer.model.GlucoseDataCode;
import com.diploma.linguistic_glucose_analyzer.model.GlucoseDataRecord;
import com.diploma.linguistic_glucose_analyzer.model.Person;
import com.diploma.linguistic_glucose_analyzer.service.*;
import com.diploma.linguistic_glucose_analyzer.service.filter.RecordFilter;
import com.diploma.linguistic_glucose_analyzer.service.filter.provider.FiltersProvider;
import com.diploma.linguistic_glucose_analyzer.service.matrix.PredictionMatrixFactory;
import com.diploma.linguistic_glucose_analyzer.service.matrix.model.PredictionMatrix;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.diploma.linguistic_glucose_analyzer.constants.LinguisticChainConstants.USED_ALPHABET;

@Slf4j
@Service
public class PredictionServiceImpl implements PredictionService {

    private static final int CHAIN_LENGTH = 1;
    private static final int RECORDS_TO_PREDICT = 5;

    private GlucoseService glucoseService;
    private LinguisticChainService linguisticChainService;
    private PredictionMatrixFactory predictionMatrixFactory;

    @Autowired
    public PredictionServiceImpl(GlucoseService glucoseService, LinguisticChainService linguisticChainService, PredictionMatrixFactory predictionMatrixFactory) {
        this.glucoseService = glucoseService;
        this.linguisticChainService = linguisticChainService;
        this.predictionMatrixFactory = predictionMatrixFactory;
    }

    @Override
    public PossibleFutureGlucoseDTO getPossibleFutureGlucoseValueForUser(long userId) {
        List<GlucoseDataRecord> allRecords = glucoseService.getAll();
        PredictionMatrix predictionMatrix = predictionMatrixFactory.getMatrix(allRecords, CHAIN_LENGTH);

        List<GlucoseDataRecord> userRecords = glucoseService.getRecordsByUser(userId);
        String userRecordsChain = linguisticChainService.getChain(userRecords);
        char lastSymbol = userRecordsChain.charAt(userRecordsChain.length() - 1);

        for (int i = 0; i < RECORDS_TO_PREDICT; i++) {
            Map<Character, Double> appearanceChances = predictionMatrix.getAppearanceChances().get(String.valueOf(lastSymbol));

            if (appearanceChances == null) {
                log.debug("UNEXPECTED LAST SYMBOL [{}]. Terminating early", lastSymbol);
                break;
            }

            double currMax = Double.MIN_VALUE;
            for (Map.Entry<Character, Double> appearanceChance : appearanceChances.entrySet()) {
                if (appearanceChance.getValue() > currMax) {
                    currMax = appearanceChance.getValue();
                    lastSymbol = appearanceChance.getKey();
                }
            }
        }

        Instant lastGlucoseRecordTime = userRecords.get(userRecords.size() - 1).getEventTime();
        SymbolBounds symbolBounds = linguisticChainService.getSymbolBounds(lastSymbol);

        return new PossibleFutureGlucoseDTO(lastGlucoseRecordTime, symbolBounds.getMin(), symbolBounds.getMax());
    }

    @Autowired
    private GlucoseFileService glucoseFileService;

    @Autowired
    private FiltersProvider filterProvider;

//        @Override
    public String getPossibleFutureGlucoseValueForUserQualityTest1(long userId) {
        List<GlucoseDataRecord> allFilteredGlucoseRecords = new ArrayList<>();

        for (int i = 4; i <= 8; i++) {
//        for (int i = 32; i <= 34; i++) {
            Person person = new Person(DiabetesType.TYPE_TWO);
            person.setId(i);
            List<GlucoseDataRecord> records = glucoseFileService.getRecords("Glucose/glucose" + getFileNumber(i));

            for (RecordFilter filter : filterProvider.getFilters()) {
                records = filter.filter(records);
            }

            for (GlucoseDataRecord record : records) {
                record.setPerson(person);
            }

            allFilteredGlucoseRecords.addAll(records);
        }

        PredictionMatrix predictionMatrix = predictionMatrixFactory.getMatrix(allFilteredGlucoseRecords, CHAIN_LENGTH);
        int num = 3;
        int overallLessThanNum = 0;
        int overallEqualNum = 0;
        int overallMoreThenNum = 0;

        for (int i = 1; i <= 2; i++) {
//        for (int i = 32; i <= 34; i++) {
            Person person = new Person(DiabetesType.TYPE_TWO);
            person.setId(i);
            List<GlucoseDataRecord> records = glucoseFileService.getRecords("Glucose/glucose" + getFileNumber(i));

            for (RecordFilter filter : filterProvider.getFilters()) {
                records = filter.filter(records);
            }

            for (GlucoseDataRecord record : records) {
                record.setPerson(person);
            }

            if (records.size() >= 500) {
                String glucoseMeasuresChain = linguisticChainService.getChain(records, USED_ALPHABET);

                for (int partitionIndex = RECORDS_TO_PREDICT - 1; partitionIndex < glucoseMeasuresChain.length() - RECORDS_TO_PREDICT; partitionIndex++) {

                    String chainPart = glucoseMeasuresChain.substring(0, partitionIndex);
                    String testChainPart = glucoseMeasuresChain.substring(partitionIndex, partitionIndex + RECORDS_TO_PREDICT);

                    char lastSymbol = chainPart.charAt(chainPart.length() - 1);
                    String symbols = "";

                    for (int j = 0; j < RECORDS_TO_PREDICT; j++) {
                        Map<Character, Double> appearanceChances = predictionMatrix.getAppearanceChances().get(String.valueOf(lastSymbol));

                        if (appearanceChances == null) {
                            log.debug("UNEXPECTED LAST SYMBOL [{}]. Terminating early", lastSymbol);
                            break;
                        }

                        double currMax = Double.MIN_VALUE;
                        for (Map.Entry<Character, Double> appearanceChance : appearanceChances.entrySet()) {
                            if (appearanceChance.getValue() > currMax) {
                                currMax = appearanceChance.getValue();
                                lastSymbol = appearanceChance.getKey();
                            }
                        }

                        symbols = symbols + lastSymbol;
                    }

                    overallLessThanNum += getLessThanDistances(symbols, testChainPart, num);
                    overallEqualNum += getEqualDistances(symbols, testChainPart, num);
                    overallMoreThenNum += getMoreThanDistances(symbols, testChainPart, num);

//                    log.debug("Expected symbols: [{}]", testChainPart);
//                    log.debug("     Got symbols: [{}]", symbols);
                }
            }
        }

        log.debug("Dist less than {}: {}", num, overallLessThanNum);
        log.debug("Dist equal {}    : {}", num, overallEqualNum);
        log.debug("Dist more than {}: {}", num, overallMoreThenNum);

        return "symbols";
    }

    private String getFileNumber(int num) {
//        return num < 10 ? "0" + num : "" + num;
        return (num < 10 ? "0" + num : "" + num) + ".csv";
    }

    @Override
    public String getPossibleFutureGlucoseValueForUserQualityTest(long userId) {
        List<GlucoseDataRecord> allFilteredGlucoseRecords = new ArrayList<>();

        for (int i = 4; i <= 8; i++) {
//        for (int i = 32; i <= 34; i++) {
            Person person = new Person(DiabetesType.TYPE_TWO);
            person.setId(i);
            List<GlucoseDataRecord> records = glucoseFileService.getRecords("Glucose/glucose" + getFileNumber(i));

            for (RecordFilter filter : filterProvider.getFilters()) {
                records = filter.filter(records);
            }

            for (GlucoseDataRecord record : records) {
                record.setPerson(person);
            }

            allFilteredGlucoseRecords.addAll(records);
        }

        PredictionMatrix predictionMatrix1 = predictionMatrixFactory.getMatrix(allFilteredGlucoseRecords, 1);
        PredictionMatrix predictionMatrix2 = predictionMatrixFactory.getMatrix(allFilteredGlucoseRecords, 2);
        PredictionMatrix predictionMatrix3 = predictionMatrixFactory.getMatrix(allFilteredGlucoseRecords, 3);
        PredictionMatrix predictionMatrix4 = predictionMatrixFactory.getMatrix(allFilteredGlucoseRecords, 4);
        PredictionMatrix predictionMatrix5 = predictionMatrixFactory.getMatrix(allFilteredGlucoseRecords, 5);

        int num = 3;
        int overallEqual0 = 0;
        int overallEqual1 = 0;
        int overallEqual2 = 0;
//        int overallLessThanNum = 0;
        int overallEqualNum = 0;
        int overallMoreThenNum = 0;

        List<Integer[]> overallNums = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Integer[] arr = new Integer[5];
            Arrays.fill(arr, 0);
            overallNums.add(arr);
        }

        for (int i = 1; i <= 2; i++) {
//        for (int i = 32; i <= 34; i++) {
            Person person = new Person(DiabetesType.TYPE_TWO);
            person.setId(i);
            List<GlucoseDataRecord> records = glucoseFileService.getRecords("Glucose/glucose" + getFileNumber(i));

            for (RecordFilter filter : filterProvider.getFilters()) {
                records = filter.filter(records);
            }

            for (GlucoseDataRecord record : records) {
                record.setPerson(person);
            }

            if (records.size() >= 500) {
                String glucoseMeasuresChain = linguisticChainService.getChain(records, USED_ALPHABET);

                for (int partitionIndex = RECORDS_TO_PREDICT; partitionIndex < glucoseMeasuresChain.length() - RECORDS_TO_PREDICT; partitionIndex++) {
                    String chainPart = glucoseMeasuresChain.substring(0, partitionIndex);
                    String testChainPart = glucoseMeasuresChain.substring(partitionIndex, partitionIndex + RECORDS_TO_PREDICT);

                    char lastSymbol = chainPart.charAt(chainPart.length() - 1);
                    String symbols = "";

                    for (int j = 0; j < RECORDS_TO_PREDICT; j++) {
                        String predBase = chainPart.substring(chainPart.length() - RECORDS_TO_PREDICT + j);
                        predBase += symbols;
                        Map<Character, Double> appearanceChances = predictionMatrix5.getAppearanceChances().get(predBase);

                        if (appearanceChances == null) {
                            predBase = predBase.substring(1);

                            appearanceChances = predictionMatrix4.getAppearanceChances().get(predBase);
                            if (appearanceChances == null) {
                                predBase = predBase.substring(1);
                                appearanceChances = predictionMatrix3.getAppearanceChances().get(predBase);
                                if (appearanceChances == null) {
                                    predBase = predBase.substring(1);
                                    appearanceChances = predictionMatrix2.getAppearanceChances().get(predBase);
                                    if (appearanceChances == null) {
                                        predBase = predBase.substring(1);
                                        appearanceChances = predictionMatrix1.getAppearanceChances().get(predBase);
                                        if (appearanceChances == null) {
                                            log.debug("UNEXPECTED LAST SYMBOL [{}]. Terminating early", lastSymbol);
                                            break;
                                        }
                                    }
                                }
                            }
                        }

                        double currMax = Double.MIN_VALUE;
                        for (Map.Entry<Character, Double> appearanceChance : appearanceChances.entrySet()) {
                            if (appearanceChance.getValue() > currMax) {
                                currMax = appearanceChance.getValue();
                                lastSymbol = appearanceChance.getKey();
                            }
                        }

                        symbols = symbols + lastSymbol;
                    }
//                    log.debug("Expected symbols: [{}]", testChainPart);
//                    log.debug("     Got symbols: [{}]", symbols);

//                    overallLessThanNum += getLessThanDistances(symbols, testChainPart, num);
                    for (int kek = 0; kek < 5; kek++) {
                        Integer[] arr = overallNums.get(kek);
                        arr[0] += getEqualDistances("" + symbols.charAt(kek), "" + testChainPart.charAt(kek), 0);
                        arr[1] += getEqualDistances("" + symbols.charAt(kek), "" + testChainPart.charAt(kek), 1);
                        arr[2] += getEqualDistances("" + symbols.charAt(kek), "" + testChainPart.charAt(kek), 2);
                        arr[3] += getLessThanDistances("" + symbols.charAt(kek), "" + testChainPart.charAt(kek), 3);
                        arr[4] += getMoreThanDistances("" + symbols.charAt(kek), "" + testChainPart.charAt(kek), 2);
                        overallNums.set(kek, arr);
                    }
//
//                    overallEqual0 += getEqualDistances(symbols, testChainPart, 0);
//                    overallEqual1 += getEqualDistances(symbols, testChainPart, 1);
//                    overallEqual2 += getEqualDistances(symbols, testChainPart, 2);
//                    overallEqualNum += getEqualDistances(symbols, testChainPart, num);
//                    overallMoreThenNum += getMoreThanDistances(symbols, testChainPart, num);
//                    log.debug("Expected symbols: [{}]", testChainPart);
//                    log.debug("     Got symbols: [{}]", symbols);
//
//                    log.debug("Dist less than {}: {}", num, getLessThanDistances(symbols, testChainPart, num));
//                    log.debug("Dist equal {}    : {}", num, getEqualDistances(symbols, testChainPart, num));
//                    log.debug("Dist more than {}: {}", num, getMoreThanDistances(symbols, testChainPart, num));
                }
            }
        }

//        log.debug("Dist equal {}    : {}", 0, overallEqual0);
//        log.debug("Dist equal {}    : {}", 1, overallEqual1);
//        log.debug("Dist equal {}    : {}", 2, overallEqual2);
////        log.debug("Dist less than {}: {}", num, overallLessThanNum);
//        log.debug("Dist equal {}    : {}", num, overallEqualNum);
//        log.debug("Dist more than {}: {}", num, overallMoreThenNum);

        for (int kek = 0; kek < 5; kek++) {
            log.debug(" - Prediction for symbol #{}", kek + 1);
            Integer[] arr = overallNums.get(kek);
            log.debug("Dist equal {}     : {}", 0, arr[0]);
            log.debug("Dist equal {}     : {}", 1, arr[1]);
            log.debug("Dist equal {}     : {}", 2, arr[2]);
            log.debug("Dist less then  {}: {}", 3, arr[3]);
            log.debug("Dist more then  {}: {}", 2, arr[4]);
            log.debug("Approx probability: {}%", (double) arr[3] / (arr[4] + arr[3]));
        }

        return "symbols";
    }

    private int getCharDistance(char charA, char charB) {
        return Math.abs((int) charA - (int) charB);
    }

    private int getLessThanDistances(String chainA, String chainB, int num) {
        int acc = 0;

        for (int i = 0; i < chainA.length(); i++) {
            int dist = getCharDistance(chainA.charAt(i), chainB.charAt(i));
            if (dist < num) {
                acc++;
            }
        }

        return acc;
    }

    private int getEqualDistances(String chainA, String chainB, int num) {
        int acc = 0;

        for (int i = 0; i < chainA.length(); i++) {
            int dist = getCharDistance(chainA.charAt(i), chainB.charAt(i));
            if (dist == num) {
                acc++;
            }
        }

        return acc;
    }

    private int getMoreThanDistances(String chainA, String chainB, int num) {
        int acc = 0;

        for (int i = 0; i < chainA.length(); i++) {
            int dist = getCharDistance(chainA.charAt(i), chainB.charAt(i));
            if (dist > num) {
                acc++;
            }
        }

        return acc;
    }
}

