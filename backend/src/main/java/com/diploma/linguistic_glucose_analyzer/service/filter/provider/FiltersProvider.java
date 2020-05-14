package com.diploma.linguistic_glucose_analyzer.service.filter.provider;

import com.diploma.linguistic_glucose_analyzer.service.filter.RecordFilter;
import org.springframework.stereotype.Service;

import java.util.List;

public interface FiltersProvider {
    List<RecordFilter> getFilters();
}
