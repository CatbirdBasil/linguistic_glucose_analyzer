package com.diploma.linguistic_glucose_analyzer.service.filter.provider;

import com.diploma.linguistic_glucose_analyzer.constants.LinguisticChainConstants;
import com.diploma.linguistic_glucose_analyzer.service.filter.GlucoseOnlyRecordFilter;
import com.diploma.linguistic_glucose_analyzer.service.filter.MinMeasuresPerDayRecordFilter;
import com.diploma.linguistic_glucose_analyzer.service.filter.RecordFilter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GlucoseFiltersProvider implements FiltersProvider {
    @Override
    public List<RecordFilter> getFilters() {
        List<RecordFilter> filters = new ArrayList<>();

        filters.add(new GlucoseOnlyRecordFilter());
        filters.add(new MinMeasuresPerDayRecordFilter(LinguisticChainConstants.MIN_MEASURES_PER_DAY_PER_PERSON));

        return filters;
    }
}
