package com._p1m.productivity_suite.config.utils;

import com._p1m.productivity_suite.config.exceptions.EntityNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collections;
import java.util.List;

public class RepositoryUtils {

    /**
     * Fetches an entity by ID or throws a standard not found exception.
     */
    public static <T> T findByIdOrThrow(JpaRepository<T, Long> repository, Long id, String entityName) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(entityName + " not found with ID: " + id));
    }

    /**
     * Fetches all entities with optional sorting.
     */
    public static <T> List<T> findAll(JpaRepository<T, Long> repository, Sort sort) {
        List<T> entities = repository.findAll(sort);
        return entities.isEmpty() ? Collections.emptyList() : entities;
    }

    public static <T> List<T> findAll(JpaRepository<T, Long> repository) {
        List<T> entities = repository.findAll();
        return entities.isEmpty() ? Collections.emptyList() : entities;
    }
}
