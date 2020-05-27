package com.diploma.linguistic_glucose_analyzer.service.impl;

import com.diploma.linguistic_glucose_analyzer.dao.UserDAO;
import com.diploma.linguistic_glucose_analyzer.dto.UserSummaryDTO;
import com.diploma.linguistic_glucose_analyzer.exception.AppException;
import com.diploma.linguistic_glucose_analyzer.model.Person;
import com.diploma.linguistic_glucose_analyzer.model.User;
import com.diploma.linguistic_glucose_analyzer.service.PersonService;
import com.diploma.linguistic_glucose_analyzer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl extends AbstractCRUDService<User, Long>
        implements UserService {
    private UserDAO userDAO;

    private PersonService personService;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, PersonService personService) {
        super(userDAO);
        this.userDAO = userDAO;
        this.personService = personService;
    }

    @Override
    public UserSummaryDTO getUserSummary(long userId) {
        User user = userDAO.findById(userId)
                .orElseThrow(() -> new AppException("No user with id" + userId + "was found"));

        Person userPersonalData = user.getPerson();

        UserSummaryDTO summary = new UserSummaryDTO();

        summary.setUsername(user.getLogin());
        summary.setEmail(user.getEmail());
        summary.setName(userPersonalData.getName());
        summary.setSurname(userPersonalData.getSurname());
        summary.setBirthDate(userPersonalData.getBirthDate());
        summary.setDiabetesTypeId(userPersonalData.getDiabetesTypeId());

        return summary;
    }

    @Override
    @Transactional
    public void updateUserData(long userId, UserSummaryDTO summary) {
        User user = userDAO.findById(userId)
                .orElseThrow(() -> new AppException("No user with id" + userId + "was found"));

        Person userPersonalData = user.getPerson();

        user.setLogin(summary.getUsername());
        user.setEmail(summary.getEmail());
        userPersonalData.setName(summary.getName());
        userPersonalData.setSurname(summary.getSurname());
        userPersonalData.setBirthDate(summary.getBirthDate());
        userPersonalData.setDiabetesTypeId(summary.getDiabetesTypeId());

        userDAO.save(user);
        personService.save(userPersonalData);
    }
}
