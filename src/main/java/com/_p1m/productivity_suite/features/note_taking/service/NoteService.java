package com._p1m.productivity_suite.features.note_taking.service;

import com._p1m.productivity_suite.features.note_taking.dto.NoteCategoryData;
import com._p1m.productivity_suite.features.note_taking.dto.NoteRequest;
import com._p1m.productivity_suite.features.note_taking.dto.NoteResponse;
import com._p1m.productivity_suite.features.note_taking.dto.NoteRetrieveOneData;

import java.util.List;

public interface NoteService {
    void createNote(final NoteRequest createNoteRequest, final String authHeader);
    List<NoteCategoryData> retrieveAll(final String authHeader);
    NoteResponse retrieveAllByCategoryId(final String authHeader, final Long categoryId);
    NoteRetrieveOneData retrieveOne(final Long id);
    void updateNote(final NoteRequest updateNoteRequest, final Long id);
    void deleteNote(final Long id);
}
