package com._p1m.productivity_suite.features.transcation.repository.jdbc;

import com._p1m.productivity_suite.features.transcation.dto.TransactionResponse;
import java.util.List;

public interface TransactionJdbcRepository {
    List<TransactionResponse> findAllByUserIdWithPagination(Long userId, int limit, int offset);
}
