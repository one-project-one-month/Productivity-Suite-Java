package com._p1m.productivity_suite.features.note_taking.dto;

public record NoteData(
        Long id,
        String title,
        String body,
        String color,
        boolean pinned,
        Long createdAt,
        Long updatedAt
) { }
