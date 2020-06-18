package com.diploma.linguistic_glucose_analyzer.controller;

import com.diploma.linguistic_glucose_analyzer.auth.JwtTokenProvider;
import com.diploma.linguistic_glucose_analyzer.dto.request.LoginRequest;
import com.diploma.linguistic_glucose_analyzer.dto.request.SignUpRequest;
import com.diploma.linguistic_glucose_analyzer.dto.response.JwtAuthenticationResponse;
import com.diploma.linguistic_glucose_analyzer.service.AuthService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("http://localhost:4200")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider tokenProvider;
    private AuthService authService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider, AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.authService = authService;
    }

    @ApiOperation(value = "authenticateUser", notes = "Takes LoginRequest dto and authenticates user")
    @ApiResponses({
            @ApiResponse(code = 500, message = "Account is not activated || User not found"),
            @ApiResponse(code = 200, message = "OK")
    })
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(new JwtAuthenticationResponse(authService.authenticateUser(
                loginRequest, authenticationManager, tokenProvider)));
    }

    @ApiOperation(value = "registerUser", notes = "Takes SignUpRequest dto and registers user")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Username already taken || Email Address already in use"),
            @ApiResponse(code = 201, message = "User registered successfully")
    })
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        return authService.registerUser(signUpRequest, passwordEncoder);
    }
}

