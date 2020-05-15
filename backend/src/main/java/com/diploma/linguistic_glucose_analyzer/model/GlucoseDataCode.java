package com.diploma.linguistic_glucose_analyzer.model;

import lombok.Getter;

@Getter
public enum GlucoseDataCode {
    REGULAR_INSULIN_DOSE(33L),
    NPH_INSULIN_DOSE(34L),
    ULTRALENTE_INSULIN_DOSE(35L),
    UNSPECIFIED_BLOOD_GLUCOSE_MEASUREMENT(48L),
    UNSPECIFIED_BLOOD_GLUCOSE_MEASUREMENT_2(57L),
    PRE_BREAKFAST_BLOOD_GLUCOSE_MEASUREMENT(58L),
    POST_BREAKFAST_BLOOD_GLUCOSE_MEASUREMENT(59L),
    PRE_LAUNCH_BLOOD_GLUCOSE_MEASUREMENT(60L),
    POST_LAUNCH_BLOOD_GLUCOSE_MEASUREMENT(61L),
    PRE_SUPPER_BLOOD_GLUCOSE_MEASUREMENT(62L),
    POST_SUPPER_BLOOD_GLUCOSE_MEASUREMENT(63L),
    PRE_SNACK_BLOOD_GLUCOSE_MEASUREMENT(64L),
    HYPOGLYCEMIC_SYMPTOMS(65L),
    TYPICAL_MEAL_INGESTION(66L),
    MORE_THAN_USUAL_MEAL_INGESTION(67L),
    LESS_THAN_USUAL_MEAL_INGESTION(68L),
    TYPICAL_EXERCISE_ACTIVITY(69L),
    MORE_THAN_USUAL_EXERCISE_ACTIVITY(70L),
    LESS_THAN_USUAL_EXERCISE_ACTIVITY(71L),
    UNSPECIFIED_SPECIAL_EVENT(72L),
    UNKNOWN_CODE(-1L);

    private final long code;

    GlucoseDataCode(long code) {
        this.code = code;
    }

    public static GlucoseDataCode valueOf(long code) {
        for (GlucoseDataCode codeValue: values()) {
            if (codeValue.code == code) {
                return codeValue;
            }
        }

        return UNKNOWN_CODE;
    }
}
