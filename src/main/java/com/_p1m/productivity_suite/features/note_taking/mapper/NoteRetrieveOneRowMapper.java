package com._p1m.productivity_suite.features.note_taking.mapper;

import com._p1m.productivity_suite.features.note_taking.dto.NoteRetrieveOneData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NoteRetrieveOneRowMapper implements RowMapper<NoteRetrieveOneData> {

    @Override
    public NoteRetrieveOneData mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new NoteRetrieveOneData(
                rs.getLong("category_id"),
                rs.getString("category_name"),
                rs.getString("category_color"),
                rs.getLong("note_id"),
                rs.getString("note_title"),
                rs.getString("note_body"),
                rs.getString("note_color"),
                rs.getBoolean("is_pinned"),
                rs.getLong("created_at"),
                rs.getLong("updated_at")
        );
    }
}
