package com.diploma.linguistic_glucose_analyzer.dao.impl;

import com.diploma.linguistic_glucose_analyzer.dao.ExtraGlucoseDAO;
import com.diploma.linguistic_glucose_analyzer.model.GlucoseDataRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Primary
@Slf4j
public class ExtraGlucoseDAOImpl implements ExtraGlucoseDAO {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String PERSON_ID_QUERY_PARAM = "personId";
    private static final String PERSON_ID_QUERY_PARAM_NAME = ":personId";

    private static final String GET_GLUCOSE_RECORDS_BY_PERSON_ID_QUERY =
            "FROM GlucoseDataRecord WHERE person.id = " + PERSON_ID_QUERY_PARAM_NAME;

    private static final String USER_ID_QUERY_PARAM = "userId";
    private static final String USER_ID_QUERY_PARAM_NAME = ":userId";

    private static final String GET_GLUCOSE_RECORDS_BY_USER_ID_QUERY =
            "FROM GlucoseDataRecord WHERE person.id = (SELECT person.id from User WHERE id = " + USER_ID_QUERY_PARAM_NAME + ")";


    @Override
    public List<GlucoseDataRecord> getRecordsByPerson(long personId) {
        TypedQuery<GlucoseDataRecord> query = entityManager.createQuery(GET_GLUCOSE_RECORDS_BY_PERSON_ID_QUERY, GlucoseDataRecord.class);

        query.setParameter(PERSON_ID_QUERY_PARAM, personId);

        return query.getResultList();
    }


    @Override
    public List<GlucoseDataRecord> getRecordsByUser(long userId) {
        TypedQuery<GlucoseDataRecord> query = entityManager.createQuery(GET_GLUCOSE_RECORDS_BY_USER_ID_QUERY, GlucoseDataRecord.class);

        query.setParameter(USER_ID_QUERY_PARAM, userId);

        return query.getResultList();
    }
}
