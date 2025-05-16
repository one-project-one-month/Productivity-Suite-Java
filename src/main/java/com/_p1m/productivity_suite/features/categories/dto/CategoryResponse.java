package com._p1m.productivity_suite.features.categories.dto;

public record CategoryResponse(
        Long id,
        String name,
        String description,
        boolean active,
        Integer typeCode,
        String typeValue,
        Long createdAt,
        Long updatedAt
) {}
