package com.diploma.linguistic_glucose_analyzer.controller;

import com.diploma.linguistic_glucose_analyzer.auth.UserPrincipal;
import com.diploma.linguistic_glucose_analyzer.service.PredictionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/prediction")
@CrossOrigin("http://localhost:4200")
@Slf4j
public class PredictionController {

    @Autowired
    private PredictionService predictionService;

    @ApiOperation(value = "getPossibleFutureGlucose", notes = "Gets possible glucose 30 mins after last record")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK")
    })
    @GetMapping()
    public ResponseEntity<?> getPossibleFutureGlucose() {
        Object potentialPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (potentialPrincipal instanceof UserPrincipal) {
            return ResponseEntity.ok(predictionService.getPossibleFutureGlucoseValueForUser(((UserPrincipal) potentialPrincipal).getId()));
        }

        return ResponseEntity.ok().build();
    }
}
