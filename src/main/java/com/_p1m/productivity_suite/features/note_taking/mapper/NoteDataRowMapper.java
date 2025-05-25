package com._p1m.productivity_suite.features.note_taking.mapper;

import com._p1m.productivity_suite.features.note_taking.dto.NoteData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NoteDataRowMapper implements RowMapper<NoteData> {

    @Override
    public NoteData mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new NoteData(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("body"),
                rs.getString("color"),
                rs.getBoolean("is_pinned"),
                rs.getLong("created_at"),
                rs.getLong("updated_at")
        );
    }
}
