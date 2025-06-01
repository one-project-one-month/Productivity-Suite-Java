package com._p1m.productivity_suite.features.categories.mapper;

import com._p1m.productivity_suite.data.enums.CategoryType;
import com._p1m.productivity_suite.features.categories.dto.CategoryResponse;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryResponseRowMapper implements RowMapper<CategoryResponse> {

        @Override
        public CategoryResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CategoryResponse(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getBoolean("active"),
                rs.getInt("type_code"),
                CategoryType.fromInt(
                        rs.getInt("type_code")
                ).getCode(),
                rs.getLong("created_at"),
                rs.getLong("updated_at")
        );
    }
}
