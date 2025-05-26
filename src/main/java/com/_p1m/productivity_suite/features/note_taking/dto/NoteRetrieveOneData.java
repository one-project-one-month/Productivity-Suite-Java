package com._p1m.productivity_suite.features.note_taking.dto;

public record NoteRetrieveOneData(
        Long categoryId,
        String categoryName,
        String categoryColor,
        Long noteId,
        String noteTitle,
        String noteBody,
        String noteColor,
        boolean pinned,
        Long createdAt,
        Long updatedAt
) {}
