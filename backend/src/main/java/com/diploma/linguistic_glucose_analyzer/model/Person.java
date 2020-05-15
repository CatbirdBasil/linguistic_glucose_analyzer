package com.diploma.linguistic_glucose_analyzer.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "diabetes_type_id", nullable = false)
    private long diabetesTypeId;

    @Column(name = "name", length = 64)
    private String name;

    @Column(name = "surname", length = 128)
    private String surname;

    @Column(name = "birth_date")
    private Timestamp birthDate;

    public Person(long diabetesTypeId) {
        this.diabetesTypeId = diabetesTypeId;
    }

    public Person(long diabetesTypeId, String name, String surname, Timestamp birthDate) {
        this.diabetesTypeId = diabetesTypeId;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
    }

    public Person(DiabetesType diabetesType) {
        this(diabetesType.getId());
    }

    public Person(DiabetesType diabetesType, String name, String surname, Timestamp birthDate) {
        this(diabetesType.getId(), name, surname, birthDate);
    }

    public DiabetesType getDiabetesType() {
        return DiabetesType.valueOf(diabetesTypeId);
    }

    public void setDiabetesType(DiabetesType type) {
        diabetesTypeId = type.getId();
    }
}
