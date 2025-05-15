package com._p1m.productivity_suite.config.utils;

import com._p1m.productivity_suite.config.exceptions.EntityNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

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

    /**
     * Fetches all entities by user ID with optional sorting using a functional repository method.
     *
     * @param repositoryMethod a method reference or lambda like (userId, sort) -> repo.findAllByUserId(userId, sort)
     */
    public static <T> List<T> findAllByUserId(Long userId, Sort sort,
                                              BiFunction<Long, Sort, List<T>> repositoryMethod) {
        List<T> results = repositoryMethod.apply(userId, sort);
        return results.isEmpty() ? Collections.emptyList() : results;
    }
}
