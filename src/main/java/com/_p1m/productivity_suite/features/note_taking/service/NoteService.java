package com._p1m.productivity_suite.features.note_taking.service;

import com._p1m.productivity_suite.features.note_taking.dto.CreateNoteRequest;
import com._p1m.productivity_suite.features.note_taking.dto.NoteResponse;
import com._p1m.productivity_suite.features.note_taking.dto.UpdateNoteRequest;

import java.util.List;

public interface NoteService {
    void createNote(final CreateNoteRequest createNoteRequest, final String authHeader);
    List<NoteResponse> retrieveAll(final String authHeader);
    NoteResponse retrieveOne(final Long id);
    void updateNote(final UpdateNoteRequest updateNoteRequest, final Long id);
    void deleteNote(final Long id);
}
