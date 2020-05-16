package com.diploma.linguistic_glucose_analyzer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponse {

    private Map<Integer, Long> usersPerYear;
    private Map<String, Long> servicesDistribution;
}
