package com.diploma.linguistic_glucose_analyzer.service.impl;

import com.diploma.linguistic_glucose_analyzer.service.CrudService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public class AbstractCRUDService<T, I> implements CrudService<T, I> {
    private final JpaRepository<T, I> repository;

    public AbstractCRUDService(JpaRepository<T, I> repository) {
        this.repository = repository;
    }

    @Override
    public T getById(I id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<T> getAll() {
        return repository.findAll();
    }

    @Override
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    public List<T> saveAll(List<T> entities) {
        return repository.saveAll(entities);
    }

    @Override
    public void delete(T entity) {
        repository.delete(entity);
    }

    @Override
    public void deleteById(I id) {
        repository.deleteById(id);
    }
}
