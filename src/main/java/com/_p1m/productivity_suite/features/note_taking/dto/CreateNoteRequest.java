package com._p1m.productivity_suite.features.note_taking.dto;

import com._p1m.productivity_suite.config.annotations.ValidNoteBody;
import com._p1m.productivity_suite.config.annotations.ValidNoteTitle;

public record CreateNoteRequest (
    @ValidNoteTitle String title,
    @ValidNoteBody String body
) {}
