package com.diploma.linguistic_glucose_analyzer.service;

import java.util.List;

public interface CrudService<T, I> {
    T getById(I id);

    List<T> getAll();

    T save(T entity);

    List<T> saveAll(List<T> entities);

    void delete(T entity);

    void deleteById(I id);
}
