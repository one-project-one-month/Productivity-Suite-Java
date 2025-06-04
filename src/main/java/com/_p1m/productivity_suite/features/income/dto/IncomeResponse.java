package com._p1m.productivity_suite.features.income.dto;

public record IncomeResponse(
        Long id,
        Long categoryId,
        String categoryName,
        String categoryColor
) {}
