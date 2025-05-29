package com._p1m.productivity_suite.config.utils;

import com._p1m.productivity_suite.config.exceptions.EntityCreationException;
import com._p1m.productivity_suite.config.exceptions.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public class PersistenceUtils {

    /**
     * Saves an entity and checks if it was successfully persisted.
     */
    public static <T extends Identifiable, R extends JpaRepository<T, Long>> T save(R repository, T entity, String entityName) {
        T saved = repository.save(entity);
        if (saved.getId() == null) {
            throw new EntityCreationException("Failed to create " + entityName);
        }
        return saved;
    }

    /**
     * Deletes an entity by ID after verifying its existence.
     */
    public static <T> void deleteById(JpaRepository<T, Long> repository, Long id, String entityName) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(entityName + " not found with ID: " + id);
        }
        repository.deleteById(id);
    }

    public interface Identifiable {
        Long getId();
    }

    /**
     * Saves a list of entities and verifies that all were successfully persisted.
     */
    public static <T extends Identifiable, R extends JpaRepository<T, Long>> void saveAll(R repository, List<T> entities, String entityName) {
        final List<T> savedEntities = repository.saveAll(entities);

        final boolean anyFailed = savedEntities
                .stream()
                .anyMatch(entity -> entity.getId() == null);
        if (anyFailed) {
            throw new EntityCreationException("Failed to create one or more " + entityName + " entities");
        }

    }
}
