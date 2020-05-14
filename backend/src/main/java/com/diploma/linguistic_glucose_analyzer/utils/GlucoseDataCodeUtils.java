package com.diploma.linguistic_glucose_analyzer.utils;

import com.diploma.linguistic_glucose_analyzer.model.GlucoseDataCode;

import java.util.Arrays;
import java.util.List;

import static com.diploma.linguistic_glucose_analyzer.model.GlucoseDataCode.*;

public class GlucoseDataCodeUtils {
    private static final List<GlucoseDataCode> INSULIN_CONSUMPTIONS = Arrays.asList(
            REGULAR_INSULIN_DOSE, NPH_INSULIN_DOSE, ULTRALENTE_INSULIN_DOSE
    );

    private static final List<GlucoseDataCode> SPECIAL_STATES = Arrays.asList(
            HYPOGLYCEMIC_SYMPTOMS
    );

    private static final List<GlucoseDataCode> PRE_MEAL_MEASUREMENTS = Arrays.asList(
            PRE_BREAKFAST_BLOOD_GLUCOSE_MEASUREMENT, PRE_LAUNCH_BLOOD_GLUCOSE_MEASUREMENT,
            PRE_SUPPER_BLOOD_GLUCOSE_MEASUREMENT, PRE_SNACK_BLOOD_GLUCOSE_MEASUREMENT
    );

    private static final List<GlucoseDataCode> POST_MEAL_MEASUREMENTS = Arrays.asList(
            POST_BREAKFAST_BLOOD_GLUCOSE_MEASUREMENT, POST_LAUNCH_BLOOD_GLUCOSE_MEASUREMENT,
            POST_SUPPER_BLOOD_GLUCOSE_MEASUREMENT
    );

    public static boolean isGlucoseMeasurement(GlucoseDataCode code) {
        return !(INSULIN_CONSUMPTIONS.contains(code) || SPECIAL_STATES.contains(code));
    }
}
