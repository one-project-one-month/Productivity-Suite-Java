package com._p1m.productivity_suite.features.income.dto;

import java.math.BigDecimal;

public record IncomeResponse(
        Long id,
        BigDecimal amount,

        Long categoryId,
        String categoryName,
        String categoryColor
) {}
