package com.diploma.linguistic_glucose_analyzer.service.impl;

import com.diploma.linguistic_glucose_analyzer.dto.PossibleFutureGlucoseDTO;
import com.diploma.linguistic_glucose_analyzer.dto.SymbolBounds;
import com.diploma.linguistic_glucose_analyzer.model.GlucoseDataRecord;
import com.diploma.linguistic_glucose_analyzer.service.GlucoseService;
import com.diploma.linguistic_glucose_analyzer.service.LinguisticChainService;
import com.diploma.linguistic_glucose_analyzer.service.PredictionService;
import com.diploma.linguistic_glucose_analyzer.service.matrix.PredictionMatrixFactory;
import com.diploma.linguistic_glucose_analyzer.service.matrix.model.PredictionMatrix;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;

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
}
