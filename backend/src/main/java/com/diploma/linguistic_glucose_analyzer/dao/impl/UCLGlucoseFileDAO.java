package com.diploma.linguistic_glucose_analyzer.dao.impl;

import com.diploma.linguistic_glucose_analyzer.constants.LinguisticChainConstants;
import com.diploma.linguistic_glucose_analyzer.dao.GlucoseFileDAO;
import com.diploma.linguistic_glucose_analyzer.model.GlucoseDataCode;
import com.diploma.linguistic_glucose_analyzer.model.GlucoseDataRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

@Slf4j
@Repository
public class UCLGlucoseFileDAO implements GlucoseFileDAO {

    /**
     * Fetch all records from file
     *
     * @param filePath relative path to file
     * @return list of all records from file
     */
    @Override
    public List<GlucoseDataRecord> getRecords(String filePath) {
        log.info("Reading from file: {}", filePath);

        if (filePath == null) {
            log.debug("Received null as param");
            return null;
        }

        var recordsFile = new File(filePath);

        if (recordsFile.exists()) {
            var records = new ArrayList<GlucoseDataRecord>();
            try (Stream<String> linesStream = Files.lines(recordsFile.toPath())) {
                linesStream.forEach(line -> {
                    String[] recordData = line.split("\t", -1);
                    GlucoseDataRecord record = getRecord(recordData);
                    if (record != null) {
                        records.add(record);
                    }
                });
            } catch (IOException ex) {
                log.debug("Error while reading file: ", ex);
            }

            log.trace("Fetched records: {}", records);
            return records;
        }

        log.debug("No file present");
        return null;
    }

    private GlucoseDataRecord getRecord(String[] recordData) {
        if (recordData == null || recordData.length != 4) {
            log.debug("Invalid data received: {}", Arrays.toString(recordData));
            return null;
        }

        var timeToParse = recordData[1] + ", " + recordData[0];

        Instant eventTime;
        try {
            eventTime = LocalDateTime.parse(timeToParse, DateTimeFormatter.ofPattern("H:mm',' M-d-uuuu", Locale.US))
                    .atZone(ZoneId.of(LinguisticChainConstants.DEFAULT_ZONE))
                    .toInstant();
        } catch (DateTimeParseException ex) {
            log.debug("Error while parsing date: ", timeToParse);
            log.debug("Stacktrace: ", ex);
            return null;
        }

        GlucoseDataCode code;
        int value;

        try {
            code = GlucoseDataCode.valueOf(Integer.parseInt(recordData[2]));
            value = Integer.parseInt(recordData[3]);
        } catch (NumberFormatException ex) {
            log.debug("Error while parsing code({}) or value({})", recordData[2], recordData[3]);
            log.debug("Stacktrace: ", ex);
            return null;
        }

        return new GlucoseDataRecord(eventTime, code, value);
    }
}
