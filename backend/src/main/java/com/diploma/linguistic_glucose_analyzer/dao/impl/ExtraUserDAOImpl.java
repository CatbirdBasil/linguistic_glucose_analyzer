package com.diploma.linguistic_glucose_analyzer.dao.impl;

import com.diploma.linguistic_glucose_analyzer.dao.ExtraUserDAO;
import com.diploma.linguistic_glucose_analyzer.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class ExtraUserDAOImpl implements ExtraUserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String LOGIN_QUERY_PARAM = "login";
    private static final String LOGIN_QUERY_PARAM_NAME = ":" + LOGIN_QUERY_PARAM;
    private static final String EMAIL_QUERY_PARAM = "email";
    private static final String EMAIL_QUERY_PARAM_NAME = ":" + EMAIL_QUERY_PARAM;

    private static final String GET_USER_BY_LOGIN_OF_EMAIL_QUERY =
            "FROM  User WHERE login = " + LOGIN_QUERY_PARAM_NAME + " OR email = " + EMAIL_QUERY_PARAM_NAME;

    private static final String GET_USER_BY_LOGIN_QUERY =
            "FROM  User WHERE login = " + LOGIN_QUERY_PARAM_NAME;

    private static final String GET_USER_BY_EMAIL_QUERY =
            "FROM  User WHERE email = " + EMAIL_QUERY_PARAM_NAME;


    @Override
    public Optional<User> getUserByUsernameOrEmail(String usernameOrEmail) {
        TypedQuery<User> query = entityManager.createQuery(GET_USER_BY_LOGIN_OF_EMAIL_QUERY, User.class);

        query.setParameter(LOGIN_QUERY_PARAM, usernameOrEmail);
        query.setParameter(EMAIL_QUERY_PARAM, usernameOrEmail);

        try {
            return Optional.of(query.getSingleResult());
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        TypedQuery<User> query = entityManager.createQuery(GET_USER_BY_LOGIN_QUERY, User.class);

        query.setParameter(LOGIN_QUERY_PARAM, username);

        return !query.getResultList().isEmpty();
    }

    @Override
    public boolean existsByEmail(String email) {
        TypedQuery<User> query = entityManager.createQuery(GET_USER_BY_EMAIL_QUERY, User.class);

        query.setParameter(EMAIL_QUERY_PARAM, email);

        return !query.getResultList().isEmpty();
    }
}
