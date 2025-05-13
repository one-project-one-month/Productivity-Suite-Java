package com._p1m.productivity_suite.features.note_taking.service;

import com._p1m.productivity_suite.features.note_taking.dto.CreateNoteRequest;
import com._p1m.productivity_suite.features.note_taking.dto.NoteResponse;
import com._p1m.productivity_suite.features.note_taking.dto.UpdateNoteRequest;

import java.util.List;

public interface NoteService {
    void createNote(final Long userId, final CreateNoteRequest createNoteRequest);
    List<NoteResponse> retrieveAllByUser(final Long userId);
    NoteResponse retrieveOne(final Long id);
    void updateNote(final Long id, final UpdateNoteRequest updateNoteRequest);
    void deleteNote(final Long id);
}
