package com.diploma.linguistic_glucose_analyzer.controller;

import com.diploma.linguistic_glucose_analyzer.auth.CurrentUser;
import com.diploma.linguistic_glucose_analyzer.auth.UserPrincipal;
import com.diploma.linguistic_glucose_analyzer.model.GlucoseDataRecord;
import com.diploma.linguistic_glucose_analyzer.model.Person;
import com.diploma.linguistic_glucose_analyzer.model.User;
import com.diploma.linguistic_glucose_analyzer.service.GlucoseFileService;
import com.diploma.linguistic_glucose_analyzer.service.GlucoseService;
import com.diploma.linguistic_glucose_analyzer.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/api/glucose")
@CrossOrigin("http://localhost:4200")
@Slf4j
public class GlucoseController {

    @Autowired
    private GlucoseService glucoseService;

    @Autowired
    private GlucoseFileService glucoseFileService;

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

    @PostMapping("/fileUpload")
    public ResponseEntity<?> uploadGlucoseRecordsFromFile(@RequestParam("file") MultipartFile file) {
        log.debug("Got file: {}", file);

        try {
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    String completeData = new String(bytes);
                    String[] rows = completeData.split("\\n");
                    List<GlucoseDataRecord> records = glucoseFileService.getRecords(rows);

                    Object potentialPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

                    if (potentialPrincipal instanceof UserPrincipal) {
                        Person person = userService.getById(((UserPrincipal) potentialPrincipal).getId()).getPerson();

                        for (GlucoseDataRecord record: records) {
                            record.setPerson(person);
                        }
                    }

                    glucoseService.saveAll(records);
                } catch (Exception e) {
                    throw new RuntimeException("FAIL!");
                }

                return ResponseEntity.status(HttpStatus.OK).body("Successfully uploaded!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Failed to upload!");
        };

        return ResponseEntity.ok().build();
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
