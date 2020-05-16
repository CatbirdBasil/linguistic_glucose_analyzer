package com.diploma.linguistic_glucose_analyzer.service;

import com.diploma.linguistic_glucose_analyzer.auth.JwtTokenProvider;
import com.diploma.linguistic_glucose_analyzer.dto.request.LoginRequest;
import com.diploma.linguistic_glucose_analyzer.dto.request.SignUpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface AuthService {
    ResponseEntity registerUser(SignUpRequest request, PasswordEncoder encoder);
    String authenticateUser(LoginRequest request, AuthenticationManager authManager, JwtTokenProvider tokenProvider);
}
