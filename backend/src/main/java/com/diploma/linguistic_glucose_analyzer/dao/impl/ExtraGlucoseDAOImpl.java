package com.diploma.linguistic_glucose_analyzer.dao.impl;

import com.diploma.linguistic_glucose_analyzer.dao.ExtraGlucoseDAO;
import com.diploma.linguistic_glucose_analyzer.model.GlucoseDataRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
@Slf4j
public class ExtraGlucoseDAOImpl implements ExtraGlucoseDAO {

    @Override
    public List<GlucoseDataRecord> getRecordsByPerson(long personId) {
        return null; //TODO Implement
    }
}
