package com.diploma.linguistic_glucose_analyzer.dao.impl;

import com.diploma.linguistic_glucose_analyzer.dao.ExtraGlucoseDAO;
import com.diploma.linguistic_glucose_analyzer.model.GlucoseDataRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class ImMemoryGlucoseDAO implements ExtraGlucoseDAO {
    private Map<Long, List<GlucoseDataRecord>> records;

    public ImMemoryGlucoseDAO() {
        this.records = new HashMap<>();
    }

    @Override
    public List<GlucoseDataRecord> getRecordsByPerson(long personId) {
        log.trace("Fetching all records from glucose repo for person id = {}", personId);

        return records.get(personId);
    }

    @Override
    public List<GlucoseDataRecord> getRecordsByUser(long userId) {
        return null;
    }
}
