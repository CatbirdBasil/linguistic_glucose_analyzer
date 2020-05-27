package com.diploma.linguistic_glucose_analyzer.controller;

import com.diploma.linguistic_glucose_analyzer.auth.UserPrincipal;
import com.diploma.linguistic_glucose_analyzer.dto.UserSummaryDTO;
import com.diploma.linguistic_glucose_analyzer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/account/users")
@CrossOrigin("http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<?> loadAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping("/summary")
    public ResponseEntity<?> getCurrentUserSummary() {
        Object potentialPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (potentialPrincipal instanceof UserPrincipal) {
            return ResponseEntity.ok(userService.getUserSummary(((UserPrincipal) potentialPrincipal).getId()));
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping("/summary")
    public ResponseEntity<?> updateCurrentUserSummary(@Valid @RequestBody UserSummaryDTO userSummary) {
        Object potentialPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (potentialPrincipal instanceof UserPrincipal) {
            userService.updateUserData(((UserPrincipal) potentialPrincipal).getId(), userSummary);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
