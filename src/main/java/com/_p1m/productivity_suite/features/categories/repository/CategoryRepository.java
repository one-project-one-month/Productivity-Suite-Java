package com._p1m.productivity_suite.features.categories.repository;

import com._p1m.productivity_suite.data.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
