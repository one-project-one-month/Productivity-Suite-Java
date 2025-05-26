package com._p1m.productivity_suite.features.note_taking.repository.jdbc;

import com._p1m.productivity_suite.features.note_taking.dto.NoteCategoryData;
import com._p1m.productivity_suite.features.note_taking.dto.NoteData;
import com._p1m.productivity_suite.features.note_taking.dto.NoteRetrieveOneData;

import java.util.List;

public interface NoteJdbcRepository {
    List<NoteData> findAllByCategoryId(final Long categoryId);
    List<NoteCategoryData> findAllByUserId(final Long userId);
    NoteRetrieveOneData findById(final Long id);
}
