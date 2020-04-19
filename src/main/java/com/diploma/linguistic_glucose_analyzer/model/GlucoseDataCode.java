package com.diploma.linguistic_glucose_analyzer.model;

public enum GlucoseDataCode {
    REGULAR_INSULIN_DOSE(33),
    NPH_INSULIN_DOSE(34),
    ULTRALENTE_INSULIN_DOSE(35),
    UNSPECIFIED_BLOOD_GLUCOSE_MEASUREMENT(48),
    UNSPECIFIED_BLOOD_GLUCOSE_MEASUREMENT_2(57),
    PRE_BREAKFAST_BLOOD_GLUCOSE_MEASUREMENT(58),
    POST_BREAKFAST_BLOOD_GLUCOSE_MEASUREMENT(59),
    PRE_LAUNCH_BLOOD_GLUCOSE_MEASUREMENT(60),
    POST_LAUNCH_BLOOD_GLUCOSE_MEASUREMENT(61),
    PRE_SUPPER_BLOOD_GLUCOSE_MEASUREMENT(62),
    POST_SUPPER_BLOOD_GLUCOSE_MEASUREMENT(63),
    PRE_SNACK_BLOOD_GLUCOSE_MEASUREMENT(64),
    HYPOGLYCEMIC_SYMPTOMS(65),
    TYPICAL_MEAL_INGESTION(66),
    MORE_THAN_USUAL_MEAL_INGESTION(67),
    LESS_THAN_USUAL_MEAL_INGESTION(68),
    TYPICAL_EXERCISE_ACTIVITY(69),
    MORE_THAN_USUAL_EXERCISE_ACTIVITY(70),
    LESS_THAN_USUAL_EXERCISE_ACTIVITY(71),
    UNSPECIFIED_SPECIAL_EVENT(72),
    UNKNOWN_CODE(-1);

    private final int code;

    GlucoseDataCode(int code) {
        this.code = code;
    }

    public static GlucoseDataCode valueOf(int code) {
        for (GlucoseDataCode codeValue: values()) {
            if (codeValue.code == code) {
                return codeValue;
            }
        }

        return UNKNOWN_CODE;
    }
}
