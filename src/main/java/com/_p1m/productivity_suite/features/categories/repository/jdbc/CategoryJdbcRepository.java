package com._p1m.productivity_suite.features.categories.repository.jdbc;

import com._p1m.productivity_suite.features.categories.dto.CategoryResponse;

import java.util.List;

public interface CategoryJdbcRepository {
    List<CategoryResponse> findByUserIdAndType(Long userId, Integer type);
}
