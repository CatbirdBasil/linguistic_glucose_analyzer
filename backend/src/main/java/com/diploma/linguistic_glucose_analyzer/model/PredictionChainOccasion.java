package com.diploma.linguistic_glucose_analyzer.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Prediction_Chain_Occasion")
public class PredictionChainOccasion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "alphabet_id", nullable = false)
    private long alphabetId;

    @Column(name = "distribution_id", nullable = false)
    private long distributionId;
//
//    @Column(name = "person_id", nullable = false)
//    private long personId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    private Person person;

    @Column(name = "linguistic_chain", nullable = false)
    private String linguisticChain;

    @Column(name = "possible_result", nullable = false)
    private String possibleResult;

    @Column(name = "occasions", nullable = false)
    private long occasions;

    public Alphabet getAlphabet() {
        return Alphabet.valueOf(alphabetId);
    }

    public void setAlphabet(Alphabet alphabet) {
        alphabetId = alphabet.getId();
    }

    public Distribution getDistribution() {
        return Distribution.valueOf(distributionId);
    }

    public void setDistribution(Distribution distribution) {
        distributionId = distribution.getId();
    }
}
