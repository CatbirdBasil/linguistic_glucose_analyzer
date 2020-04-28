package com.diploma.linguistic_glucose_analyzer.service.impl;

import com.diploma.linguistic_glucose_analyzer.dao.GlucoseDAO;
import com.diploma.linguistic_glucose_analyzer.model.GlucoseDataRecord;
import com.diploma.linguistic_glucose_analyzer.service.GlucoseService;
import com.diploma.linguistic_glucose_analyzer.utils.GlucoseDataCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

//import org.springframework.transaction.annotation.Transactional;

@Service
public class GlucoseServiceImpl implements GlucoseService {

    @Autowired
    private GlucoseDAO glucoseDAO;

    @Override
    public List<GlucoseDataRecord> getAllRecords() {
        return glucoseDAO.getAllRecords();
    }

    @Override
    public List<GlucoseDataRecord> getRecordsByPerson(long personId) {
        return glucoseDAO.getRecordsByPerson(personId);
    }

    @Override
//    @Transactional
    public void saveRecordsForPerson(List<GlucoseDataRecord> records, long personId) {
        glucoseDAO.saveRecordsForPerson(records, personId);
    }
}
