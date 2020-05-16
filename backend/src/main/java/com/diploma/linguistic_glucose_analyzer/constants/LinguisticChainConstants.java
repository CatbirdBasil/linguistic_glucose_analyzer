package com.diploma.linguistic_glucose_analyzer.constants;

import com.diploma.linguistic_glucose_analyzer.dto.response.ApiResponse;
import com.diploma.linguistic_glucose_analyzer.model.Alphabet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface LinguisticChainConstants {
    int MIN_GLUCOSE = 0;
    int MAX_GLUCOSE = 520;

    int GLUCOSE_MIN_HEALTHY = 40;
    int GLUCOSE_MAX_HEALTHY = 200;

    int MIN_MEASURES_PER_DAY_PER_PERSON = 2;

//    Alphabet USED_ALPHABET = Alphabet.ASCII100;
    Alphabet USED_ALPHABET = Alphabet.ENGLISH;

    String DEFAULT_ZONE = "America/Toronto";

    String DEFAULT_AUTHORITY = "USER";

    interface ResponseEntities {
        ResponseEntity BAD_REQ_USERNAME_TAKEN = new ResponseEntity<>(new ApiResponse(
                false, "Username is already taken!"
        ), HttpStatus.BAD_REQUEST);

        ResponseEntity BAD_REQ_EMAIL_TAKEN = new ResponseEntity<>(new ApiResponse(
                false, "Email Address already in use!"
        ), HttpStatus.BAD_REQUEST);

        ResponseEntity USER_REGISTERED_SUCCESSFULLY = new ResponseEntity<>(new ApiResponse(
                true, "User registered successfully"
        ), HttpStatus.CREATED);
    }
}
