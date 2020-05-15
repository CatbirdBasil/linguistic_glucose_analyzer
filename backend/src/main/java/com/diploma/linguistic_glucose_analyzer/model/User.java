package com.diploma.linguistic_glucose_analyzer.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class User {
    private long id;
    private long personId;
    private Person person;
    private String login;
    private String password;
    private String email;
    private Timestamp registrationDate;
    private String isEmailVerified;
}
