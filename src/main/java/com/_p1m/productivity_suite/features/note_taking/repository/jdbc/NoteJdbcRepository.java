package com._p1m.productivity_suite.features.note_taking.repository.jdbc;

import com._p1m.productivity_suite.features.note_taking.dto.NoteData;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface NoteJdbcRepository {
    List<NoteData> findAllByCategoryId(final Integer categoryId);
}
