package com._p1m.productivity_suite.features.transcation.repository.jdbc;

import com._p1m.productivity_suite.features.transcation.dto.TransactionResponse;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionJdbcRepository {
    List<TransactionResponse> findAllByUserIdWithPagination(Long userId, int limit, int offset);
    List<TransactionResponse> searchTransactions(
            Long userId,
            Long categoryId,
            String description,
            Long transactionDate,
            BigDecimal fromAmount,
            BigDecimal toAmount,
            int limit,
            int offset
    );
}
