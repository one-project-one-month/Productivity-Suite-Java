package com._p1m.productivity_suite.features.note_taking.dto;

import com._p1m.productivity_suite.config.annotations.ValidCategoryId;
import com._p1m.productivity_suite.config.annotations.ValidNoteBody;
import com._p1m.productivity_suite.config.annotations.ValidNoteColor;
import com._p1m.productivity_suite.config.annotations.ValidNoteTitle;

public record NoteRequest (
        @ValidNoteTitle String title,
        @ValidNoteBody String body,
        @ValidCategoryId Long categoryId,
        @ValidNoteColor String color
) {}
