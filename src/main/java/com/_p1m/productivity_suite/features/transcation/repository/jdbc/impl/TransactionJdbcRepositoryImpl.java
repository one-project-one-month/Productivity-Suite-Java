package com._p1m.productivity_suite.features.transcation.repository.jdbc.impl;

import com._p1m.productivity_suite.features.transcation.dto.TransactionResponse;
import com._p1m.productivity_suite.features.transcation.mapper.TransactionResponseRowMapper;
import com._p1m.productivity_suite.features.transcation.repository.jdbc.TransactionJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
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

    @Override
    public List<TransactionResponse> searchTransactions(final Long userId, final Long categoryId, final String description, final Long transactionDate, final BigDecimal fromAmount, final BigDecimal toAmount, final int limit, final int offset) {
        final StringBuilder sql = new StringBuilder("""
            SELECT id, amount, description, transaction_date, created_at, updated_at
            FROM transaction
            WHERE user_id = ?
        """);

        final List<Object> params = new ArrayList<>();
        params.add(userId);

        if (categoryId != null) {
            sql.append(" AND category_id = ?");
            params.add(categoryId);
        }

        if (description != null && !description.trim().isEmpty()) {
            sql.append(" AND LOWER(description) LIKE ?");
            params.add("%" + description.toLowerCase() + "%");
        }

        if (transactionDate != null) {
            final ZoneId zoneId = ZoneId.systemDefault();
            final Instant instant = Instant.ofEpochSecond(transactionDate);
            final LocalDate localDate = instant.atZone(zoneId).toLocalDate();

            final Long startOfDay = localDate.atStartOfDay(zoneId).toEpochSecond();
            final Long endOfDay = localDate.plusDays(1).atStartOfDay(zoneId).toEpochSecond();

            sql.append(" AND transaction_date >= ? AND transaction_date < ?");
            params.add(startOfDay);
            params.add(endOfDay);
        }

        if (fromAmount != null) {
            sql.append(" AND amount >= ?");
            params.add(fromAmount);
        }

        if (toAmount != null) {
            sql.append(" AND amount <= ?");
            params.add(toAmount);
        }

        sql.append(" ORDER BY created_at DESC LIMIT ? OFFSET ?");
        params.add(limit);
        params.add(offset);

        return jdbcTemplate.query(sql.toString(), ROW_MAPPER, params.toArray());
    }
}
