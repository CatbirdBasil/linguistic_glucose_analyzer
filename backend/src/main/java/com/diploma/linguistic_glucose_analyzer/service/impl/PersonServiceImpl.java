package com.diploma.linguistic_glucose_analyzer.service.impl;

import com.diploma.linguistic_glucose_analyzer.dao.PersonDAO;
import com.diploma.linguistic_glucose_analyzer.model.Person;
import com.diploma.linguistic_glucose_analyzer.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl extends AbstractCRUDService<Person, Long>
        implements PersonService {
    private PersonDAO personDAO;

    @Autowired
    public PersonServiceImpl(PersonDAO personDAO) {
        super(personDAO);
        this.personDAO = personDAO;
    }
}
