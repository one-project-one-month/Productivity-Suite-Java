package com._p1m.productivity_suite.features.expensesummary.repository.impl;

import com._p1m.productivity_suite.features.expensesummary.repository.ExpenseSummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ExpenseSummaryRepositoryImpl implements ExpenseSummaryRepository {

    private final JdbcTemplate jdbcTemplate;


    @Override
    public List<Map<String, Object>> findDailySummaryByUser(final Long userId) {
        String sql = """
        SELECT
            t.transaction_date,
            c.name AS category,
            t.amount
        FROM transaction t
        JOIN categories c ON t.category_id = c.id
        WHERE t.user_id = ?
        ORDER BY t.transaction_date, c.name
    """;

        final List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, userId);

        DateTimeFormatter desiredFormatter = DateTimeFormatter.ofPattern("d.M.yy");

        // Map<date_string, Map<category, total>>
        final Map<String, Map<String, BigDecimal>> grouped = new LinkedHashMap<>();

        for (Map<String, Object> row : rows) {
            Number transactionDateNum = (Number) row.get("transaction_date");
            long transactionDateMillis = transactionDateNum.longValue();

            LocalDate date = Instant.ofEpochSecond(transactionDateMillis)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            String formattedDate = date.format(desiredFormatter);

            String category = (String) row.get("category");
            BigDecimal amount = (BigDecimal) row.get("amount");

            grouped.computeIfAbsent(formattedDate, d -> new LinkedHashMap<>())
                    .merge(category, amount, BigDecimal::add);
        }

        // Convert to List<Map<String,Object>>
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map.Entry<String, Map<String, BigDecimal>> dateEntry : grouped.entrySet()) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("date", dateEntry.getKey());
            map.putAll(dateEntry.getValue());
            result.add(map);
        }

        return result;
    }

}
