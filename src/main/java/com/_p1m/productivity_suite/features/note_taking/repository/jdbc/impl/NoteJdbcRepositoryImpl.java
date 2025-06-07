package com._p1m.productivity_suite.features.note_taking.repository.jdbc.impl;

import com._p1m.productivity_suite.config.exceptions.EntityNotFoundException;
import com._p1m.productivity_suite.features.note_taking.dto.NoteCategoryData;
import com._p1m.productivity_suite.features.note_taking.dto.NoteData;
import com._p1m.productivity_suite.features.note_taking.dto.NoteResponse;
import com._p1m.productivity_suite.features.note_taking.dto.NoteRetrieveOneData;
import com._p1m.productivity_suite.features.note_taking.mapper.NoteCategoryDataRowMapper;
import com._p1m.productivity_suite.features.note_taking.mapper.NoteDataRowMapper;
import com._p1m.productivity_suite.features.note_taking.mapper.NoteRetrieveOneRowMapper;
import com._p1m.productivity_suite.features.note_taking.repository.jdbc.NoteJdbcRepository;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class NoteJdbcRepositoryImpl implements NoteJdbcRepository {

    private final JdbcTemplate jdbcTemplate;
    private static final NoteDataRowMapper NOTE_DATA_ROW_MAPPER = new NoteDataRowMapper();
    private static final NoteCategoryDataRowMapper NOTE_CATEGORY_DATA_ROW_MAPPER = new NoteCategoryDataRowMapper();
    private static final NoteRetrieveOneRowMapper NOTE_RETRIEVE_ONE_ROW_MAPPER = new NoteRetrieveOneRowMapper();

    private static final String FIND_ALL_BY_CATEGORY_ID_QUERY = """
        SELECT
            note.id AS id, note.title AS title, note.body AS body, note.color AS color, note.is_pinned AS is_pinned, note.created_at AS created_at, note.updated_at AS updated_at
        FROM note note
        WHERE note.category_id = ?;
    """;

    private static final String FIND_ALL_BY_USER_ID_QUERY = """
        SELECT
            c.id AS category_id,
            c.name AS category_name,
            c.description AS category_color,
            COUNT(n.id) AS number_of_notes
        FROM
            categories c
        LEFT JOIN
            note n ON n.category_id = c.id
        WHERE
            c.user_id = ? AND c.type = '4'
        GROUP BY
            c.id, c.name, c.description;
    """;

    private static final String FIND_BY_ID_QUERY = """
        SELECT
            c.id AS category_id,
            c.name AS category_name,
            c.description AS category_color,
            n.id AS note_id,
            n.title AS note_title,
            n.body AS note_body,
            n.color AS note_color,
            n.is_pinned AS is_pinned,
            n.created_at AS created_at,
            n.updated_at AS updated_at
        FROM
            note n
        LEFT JOIN
            categories c ON c.id = n.category_id
        WHERE
            n.id = ?;
    """;


    @Override
    public List<NoteData> findAllByCategoryId(final Long categoryId) {
        return this.jdbcTemplate.query(
                FIND_ALL_BY_CATEGORY_ID_QUERY,
                NOTE_DATA_ROW_MAPPER,
                categoryId
        );
    }

    @Override
    public List<NoteCategoryData> findAllByUserId(Long userId) {
        return this.jdbcTemplate.query(
                FIND_ALL_BY_USER_ID_QUERY,
                NOTE_CATEGORY_DATA_ROW_MAPPER,
                userId
        );
    }

    @Override
    public NoteRetrieveOneData findById(Long id) {
        return this.findOne(id)
                .orElseThrow(() -> new EntityNotFoundException("Note not found with id " + id));
    }

    private Optional<NoteRetrieveOneData> findOne(final Long id) {
        return jdbcTemplate.query(FIND_BY_ID_QUERY, NOTE_RETRIEVE_ONE_ROW_MAPPER, id)
                .stream().findFirst();
    }
}
