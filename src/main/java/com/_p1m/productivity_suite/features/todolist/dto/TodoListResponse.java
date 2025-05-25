package com._p1m.productivity_suite.features.todolist.dto;

public record TodoListResponse(
        Long   id,
        String title,
        String description,
        Integer statusCode,
        String statusValue,
        Integer priorityCode,
        String priorityValue,
        Long   completedAt,
        Long   dueAt,
        Long   createdAt,
        Long   updatedAt
) {}
