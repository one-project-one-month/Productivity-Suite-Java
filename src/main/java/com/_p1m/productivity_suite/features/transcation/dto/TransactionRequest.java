package com._p1m.productivity_suite.features.transcation.dto;

import com._p1m.productivity_suite.config.annotations.ValidTransactionAmount;
import com._p1m.productivity_suite.config.annotations.ValidTransactionDate;
import com._p1m.productivity_suite.config.annotations.ValidTransactionDescription;

import java.math.BigDecimal;

public record TransactionRequest(
        @ValidTransactionAmount  BigDecimal amount,
        @ValidTransactionDescription String description,
        @ValidTransactionDate Long transactionDate,
        Long categoryId
) {}
