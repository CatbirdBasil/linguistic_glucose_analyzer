package com.diploma.linguistic_glucose_analyzer.service.filter.provider;

import com.diploma.linguistic_glucose_analyzer.service.filter.RecordFilter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FiltersProvider {
    List<RecordFilter> provideFilters();
}
