package com._p1m.productivity_suite.features.note_taking.repository.jdbc.impl;

import com._p1m.productivity_suite.features.note_taking.dto.NoteData;
import com._p1m.productivity_suite.features.note_taking.mapper.NoteDataRowMapper;
import com._p1m.productivity_suite.features.note_taking.repository.jdbc.NoteJdbcRepository;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class NoteJdbcRepositoryImpl implements NoteJdbcRepository {

    private final JdbcTemplate jdbcTemplate;
    private static final NoteDataRowMapper NOTE_DATA_ROW_MAPPER = new NoteDataRowMapper();

    private static final String FIND_ALL_BY_CATEGORY_ID_QUERY = """
        SELECT
            note.id AS id, note.title AS title, note.body AS body, note.color AS color, note.is_pinned AS is_pinned, note.created_at AS created_at, note.updated_at AS updated_at
        FROM note note
        WHERE note.category_id = ?;
    """;

    @Override
    public List<NoteData> findAllByCategoryId(final Integer categoryId) {
        return jdbcTemplate.query(
                FIND_ALL_BY_CATEGORY_ID_QUERY,
                NOTE_DATA_ROW_MAPPER,
                categoryId
        );
    }
}
