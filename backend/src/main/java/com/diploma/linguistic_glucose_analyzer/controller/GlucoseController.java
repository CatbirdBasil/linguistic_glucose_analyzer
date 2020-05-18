package com.diploma.linguistic_glucose_analyzer.controller;

import com.diploma.linguistic_glucose_analyzer.auth.CurrentUser;
import com.diploma.linguistic_glucose_analyzer.auth.UserPrincipal;
import com.diploma.linguistic_glucose_analyzer.model.GlucoseDataRecord;
import com.diploma.linguistic_glucose_analyzer.model.Person;
import com.diploma.linguistic_glucose_analyzer.model.User;
import com.diploma.linguistic_glucose_analyzer.service.GlucoseService;
import com.diploma.linguistic_glucose_analyzer.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/glucose")
@CrossOrigin("http://localhost:4200")
@Slf4j
public class GlucoseController {

    @Autowired
    private GlucoseService glucoseService;

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<?> getAllGlucoseRecords() {
        return ResponseEntity.ok(glucoseService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGlucoseRecord(@PathVariable long id) {
        return ResponseEntity.ok(glucoseService.getById(id));
    }

    @GetMapping("/person/{id}")
    public ResponseEntity<?> getGlucoseRecordByPersonId(@PathVariable long id) {
        return ResponseEntity.ok(glucoseService.getRecordsByPerson(id));
    }

    @GetMapping("/user")
    public ResponseEntity<?> getGlucoseRecordByPersonId() {
        Object potentialPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (potentialPrincipal instanceof UserPrincipal) {
            return ResponseEntity.ok(glucoseService.getRecordsByUser(((UserPrincipal) potentialPrincipal).getId()));
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> createGlucoseRecord(@Valid @RequestBody GlucoseDataRecord record) {
        Object potentialPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (potentialPrincipal instanceof UserPrincipal) {
            Person person = userService.getById(((UserPrincipal) potentialPrincipal).getId()).getPerson();
            record.setPerson(person);
        }

        return ResponseEntity.ok(glucoseService.save(record));
    }

    @PutMapping
    public ResponseEntity<?> updateGlucoseRecord(@Valid @RequestBody GlucoseDataRecord record) {
        Object potentialPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (potentialPrincipal instanceof UserPrincipal) {
            Person person = userService.getById(((UserPrincipal) potentialPrincipal).getId()).getPerson();
            record.setPerson(person);
        }

        glucoseService.save(record);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGlucoseRecord(@PathVariable long id) {
        glucoseService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
