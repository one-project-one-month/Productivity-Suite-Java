package com._p1m.productivity_suite.features.transcation.repository.jdbc.impl;

import com._p1m.productivity_suite.features.transcation.dto.TransactionResponse;
import com._p1m.productivity_suite.features.transcation.mapper.TransactionResponseRowMapper;
import com._p1m.productivity_suite.features.transcation.repository.jdbc.TransactionJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TransactionJdbcRepositoryImpl implements TransactionJdbcRepository {

    private final JdbcTemplate jdbcTemplate;
    private static final TransactionResponseRowMapper ROW_MAPPER = new TransactionResponseRowMapper();

    private static final String FIND_ALL_WITH_PAGINATION = """
        SELECT
            id, amount, description, transaction_date, created_at, updated_at
        FROM
            transaction
        WHERE
            user_id = ?
        ORDER BY
            created_at DESC
        LIMIT ?
        OFFSET ?;
    """;

    @Override
    public List<TransactionResponse> findAllByUserIdWithPagination(Long userId, int limit, int offset) {
        return jdbcTemplate.query(
                FIND_ALL_WITH_PAGINATION,
                ROW_MAPPER,
                userId, limit, offset
        );
    }
}
