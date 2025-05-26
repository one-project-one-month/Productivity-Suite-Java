package com._p1m.productivity_suite.features.note_taking.mapper;

import com._p1m.productivity_suite.features.note_taking.dto.NoteCategoryData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NoteCategoryDataRowMapper implements RowMapper<NoteCategoryData> {

    @Override
    public NoteCategoryData mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new NoteCategoryData(
                rs.getLong("category_id"),
                rs.getString("category_name"),
                rs.getString("category_color"),
                rs.getInt("number_of_notes")
        );
    }
}
