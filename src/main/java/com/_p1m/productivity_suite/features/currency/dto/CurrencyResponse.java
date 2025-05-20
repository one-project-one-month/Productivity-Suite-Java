package com._p1m.productivity_suite.features.currency.dto;

public record CurrencyResponse(
        Long id,
        String name,
        boolean active,
        Long createdAt,
        Long updatedAt
) {}
