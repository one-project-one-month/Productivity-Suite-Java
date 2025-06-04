package com._p1m.productivity_suite.features.income.dto;

import com._p1m.productivity_suite.config.annotations.ValidCategoryId;

import java.math.BigDecimal;

public record IncomeRequest(
        BigDecimal amount,
        @ValidCategoryId Long categoryId,
        Long userId
) {}
