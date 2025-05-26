package com._p1m.productivity_suite.features.note_taking.dto;

public record NoteCategoryData(
        Long categoryId,
        String categoryName,
        String color,
        Integer numberOfNotes
) {}
