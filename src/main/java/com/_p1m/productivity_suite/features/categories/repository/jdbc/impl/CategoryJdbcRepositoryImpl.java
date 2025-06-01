package com._p1m.productivity_suite.features.categories.repository.jdbc.impl;

import com._p1m.productivity_suite.features.categories.dto.CategoryResponse;
import com._p1m.productivity_suite.features.categories.mapper.CategoryResponseRowMapper;
import com._p1m.productivity_suite.features.categories.repository.jdbc.CategoryJdbcRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryJdbcRepositoryImpl implements CategoryJdbcRepository {

    private final JdbcTemplate jdbcTemplate;
    private static final CategoryResponseRowMapper CATEGORY_RESPONSE_ROW_MAPPER = new CategoryResponseRowMapper();

    public CategoryJdbcRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String FIND_ALL_BY_USER_ID_AND_TYPE = """
        SELECT
            id, name, description, active, type as type_code, null as type_value, created_at, updated_at
        FROM categories WHERE user_id = ? and type = ?
    """;

    @Override
    public List<CategoryResponse> findByUserIdAndType(final Long userId, final Integer type) {
        return this.jdbcTemplate.query(
                FIND_ALL_BY_USER_ID_AND_TYPE,
                CATEGORY_RESPONSE_ROW_MAPPER,
                userId, type
        );
    }
}
