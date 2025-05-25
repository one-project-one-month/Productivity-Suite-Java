package com._p1m.productivity_suite.features.transcation.dto;

import java.math.BigDecimal;

public record TransactionResponse(
        Long id,
        BigDecimal amount,
        String description,
        Long transactionDate,
        Long createdAt,
        Long updatedAt
) { }
