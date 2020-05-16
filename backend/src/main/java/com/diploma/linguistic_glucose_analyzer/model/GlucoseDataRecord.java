package com.diploma.linguistic_glucose_analyzer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Glucose_Data_Record")
public class GlucoseDataRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
//
//    @Column(name = "person_id", nullable = false)
//    private long personId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    private Person person;

    @Column(name = "record_time", nullable = false)
    private Instant eventTime; //TODO Possible problems with date saving to db

    @Column(name = "record_type_id", nullable = false)
    private long codeId;

    @Column(name = "value", nullable = false)
    private int value;

    public GlucoseDataRecord(Instant eventTime, GlucoseDataCode code, int value) {
        this.eventTime = eventTime;
        setCode(code);
        this.value = value;
    }

    public GlucoseDataCode getCode() {
        return GlucoseDataCode.valueOf(codeId);
    }

    public void setCode(GlucoseDataCode code) {
        codeId = code.getCode();
    }
}
