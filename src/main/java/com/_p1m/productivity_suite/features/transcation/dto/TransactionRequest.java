package com._p1m.productivity_suite.features.transcation.dto;

import java.math.BigDecimal;

public record TransactionRequest(
        BigDecimal amount,
        String description,
        Long transactionDate,
        Long categoryId
) {}
