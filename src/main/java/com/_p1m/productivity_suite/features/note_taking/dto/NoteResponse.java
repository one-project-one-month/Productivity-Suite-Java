package com._p1m.productivity_suite.features.note_taking.dto;

public record NoteResponse(
        Long id,
        String title,
        String body,
        Long createdAt,
        Long updatedAt
) { }
