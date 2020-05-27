package com.diploma.linguistic_glucose_analyzer.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class UserSummaryDTO {
    private String username;
    private String email;
    private String name;
    private String surname;
    private Instant birthDate;
    private long diabetesTypeId;
}
