package com._p1m.productivity_suite.features.note_taking.dto;

import java.util.List;

public record NoteResponse(
        Long categoryId,
        String categoryName,
        List<NoteData> notes
) { }
