package com.diploma.linguistic_glucose_analyzer.dao;

import com.diploma.linguistic_glucose_analyzer.dao.ExtraGlucoseDAO;
import com.diploma.linguistic_glucose_analyzer.model.GlucoseDataRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlucoseDAO extends JpaRepository<GlucoseDataRecord, Long>, ExtraGlucoseDAO {
}
