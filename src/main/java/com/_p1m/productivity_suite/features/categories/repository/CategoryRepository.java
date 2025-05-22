package com._p1m.productivity_suite.features.categories.repository;

import com._p1m.productivity_suite.data.models.Category;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByUserId(Long userId, Sort sort);
    boolean existsByIdAndUserId(Long id, Long userId);
}
