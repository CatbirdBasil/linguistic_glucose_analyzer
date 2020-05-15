package com.diploma.linguistic_glucose_analyzer.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Person {
    private long id;
    private long diabetesTypeId;
    private DiabetesType diabetesType;
    private String name;
    private String surname;
    private Timestamp birthDate;
}
