package com.diploma.linguistic_glucose_analyzer.dao.impl;

import com.diploma.linguistic_glucose_analyzer.dao.GlucoseDAO;
import com.diploma.linguistic_glucose_analyzer.dao.GlucoseFileDAO;
import com.diploma.linguistic_glucose_analyzer.model.GlucoseDataRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class ImMemoryGlucoseDAO implements GlucoseDAO {
    private Map<Long, List<GlucoseDataRecord>> records;

    public ImMemoryGlucoseDAO() {
        this.records = new HashMap<>();
    }

    @Override
    public List<GlucoseDataRecord> getAllRecords() {
        log.trace("Fetching all records from glucose repo");

        return records.values().stream()
                .reduce(new ArrayList<>(), (list, elem) -> {
                    list.addAll(elem);
                    return list;
                });
    }

    @Override
    public List<GlucoseDataRecord> getRecordsByPerson(long personId) {
        log.trace("Fetching all records from glucose repo for person id = {}", personId);

        return records.get(personId);
    }

    @Override
    public void saveRecordsForPerson(List<GlucoseDataRecord> newRecords) {
        log.trace("Saving records {} to glucos", newRecords);

        long personId = 1;
        if (newRecords != null && newRecords.size() > 0) {
            personId = newRecords.get(0).getPersonId();
        }

        List<GlucoseDataRecord> personRecords = records.get(personId);

        for (GlucoseDataRecord record : newRecords) {
            record.setPersonId(personId);
        }

        if (personRecords == null) {
            records.put(personId, newRecords);
        } else {
            personRecords.addAll(newRecords);
        }
    }
}
