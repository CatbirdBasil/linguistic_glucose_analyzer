package com.diploma.linguistic_glucose_analyzer.service.impl;

import com.diploma.linguistic_glucose_analyzer.dao.GlucoseDAO;
import com.diploma.linguistic_glucose_analyzer.model.GlucoseDataRecord;
import com.diploma.linguistic_glucose_analyzer.service.GlucoseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class GlucoseServiceImpl extends AbstractCRUDService<GlucoseDataRecord, Long>
        implements GlucoseService {

    private GlucoseDAO glucoseDAO;

    @Autowired
    public GlucoseServiceImpl(GlucoseDAO glucoseDAO) {
        super(glucoseDAO);
        this.glucoseDAO = glucoseDAO;
    }

    @Override
    public List<GlucoseDataRecord> getRecordsByPerson(long personId) {
        return glucoseDAO.getRecordsByPerson(personId);
    }

    @Override
    public List<GlucoseDataRecord> getRecordsByUser(long userId) {
        return glucoseDAO.getRecordsByUser(userId);
    }
}
