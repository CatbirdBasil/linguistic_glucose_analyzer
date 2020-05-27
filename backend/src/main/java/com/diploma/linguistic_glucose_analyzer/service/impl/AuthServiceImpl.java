package com.diploma.linguistic_glucose_analyzer.service.impl;

import com.diploma.linguistic_glucose_analyzer.auth.JwtTokenProvider;
import com.diploma.linguistic_glucose_analyzer.constants.LinguisticChainConstants;
import com.diploma.linguistic_glucose_analyzer.dao.PersonDAO;
import com.diploma.linguistic_glucose_analyzer.dao.UserDAO;
import com.diploma.linguistic_glucose_analyzer.dto.request.LoginRequest;
import com.diploma.linguistic_glucose_analyzer.dto.request.SignUpRequest;
import com.diploma.linguistic_glucose_analyzer.model.DiabetesType;
import com.diploma.linguistic_glucose_analyzer.model.Person;
import com.diploma.linguistic_glucose_analyzer.model.User;
import com.diploma.linguistic_glucose_analyzer.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserDAO userDao;

    @Autowired
    private PersonDAO personDAO;

    @Transactional
    public ResponseEntity registerUser(SignUpRequest request, PasswordEncoder encoder) {

        if (userDao.existsByUsername(request.getUsername())) {
            return LinguisticChainConstants.ResponseEntities.BAD_REQ_USERNAME_TAKEN;
        }
        if (userDao.existsByEmail(request.getEmail())) {
            return LinguisticChainConstants.ResponseEntities.BAD_REQ_EMAIL_TAKEN;
        }

        Person person =
                new Person(request.getDiabetesTypeId(), request.getFirstName(), request.getLastName(), request.getBirthDate());

        personDAO.save(person);

        User user = new User(person, request.getUsername(),
                encoder.encode(request.getPassword()), request.getEmail(), Timestamp.from(Instant.now()));

        userDao.save(user);

        return LinguisticChainConstants.ResponseEntities.USER_REGISTERED_SUCCESSFULLY;
    }

    public String authenticateUser(LoginRequest request, AuthenticationManager authManager, JwtTokenProvider tokenProvider) {
        log.debug("Request: {}|{}", request.getUsernameOrEmail(), request.getPassword());
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsernameOrEmail(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return tokenProvider.generateToken(authentication);
    }
}
