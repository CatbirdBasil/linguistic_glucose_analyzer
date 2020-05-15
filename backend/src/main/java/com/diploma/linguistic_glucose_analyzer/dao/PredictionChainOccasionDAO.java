package com.diploma.linguistic_glucose_analyzer.dao;

import com.diploma.linguistic_glucose_analyzer.model.PredictionChainOccasion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PredictionChainOccasionDAO
        extends JpaRepository<PredictionChainOccasion, Long>, ExtraPredictionChainOccasionDAO {
}
