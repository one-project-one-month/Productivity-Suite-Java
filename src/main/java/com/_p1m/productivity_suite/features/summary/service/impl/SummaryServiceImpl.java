package com._p1m.productivity_suite.features.summary.service.impl;

import com._p1m.productivity_suite.features.summary.dto.BudgetSpentResponse;
import com._p1m.productivity_suite.features.summary.dto.FocusTimeResponse;
import com._p1m.productivity_suite.features.summary.dto.TaskCompleteResponse;
import com._p1m.productivity_suite.features.summary.service.SummaryService;
import com._p1m.productivity_suite.features.users.dto.response.UserDto;
import com._p1m.productivity_suite.features.users.utils.UserUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SummaryServiceImpl implements SummaryService {

    @PersistenceContext
    private final EntityManager entityManager;
    private final UserUtil userUtil;

    @Override
    public List<FocusTimeResponse> retrievePomodoroWeeklyFocusTimeSummary(String authHeader) {
        final UserDto userDto = this.userUtil.getCurrentUserDto(authHeader);

        // Filter the duration with  sequence status, user's id, timer_sequence step, and timer remaining_time
        String totalFocusTimeQuery = """
            WITH days AS (
              SELECT
                CAST(generate_series(
                  CURRENT_DATE - INTERVAL '6 days',
                  CURRENT_DATE,
                  INTERVAL '1 day'
                ) AS date) AS day
            ),
            durations AS (
              SELECT
                DATE(TO_TIMESTAMP(t.created_at / 1000)) AS day,
                SUM(t.duration) AS total_durations
              FROM timer t
              JOIN timer_sequence ts ON t.id = ts.timer_id
              JOIN sequence s ON ts.sequence_id = s.id
              WHERE s.status = 't'
                AND s.user_id = ?
                AND ts.step IN (0, 2, 4, 6)
                AND t.remaining_time = 0
                AND DATE(TO_TIMESTAMP(t.created_at / 1000)) BETWEEN CURRENT_DATE - INTERVAL '6 days' AND CURRENT_DATE
              GROUP BY day
            )
            SELECT
              TO_CHAR(d.day, 'FMDay') AS week_day,
              COALESCE(du.total_durations, 0) AS total_durations
            FROM days d
            LEFT JOIN durations du ON d.day = du.day
            ORDER BY d.day;
        """;

        Query query = entityManager.createNativeQuery(totalFocusTimeQuery);
        query.setParameter(1, userDto.getId());

        @SuppressWarnings("unchecked")
        List<Object[]> results = query.getResultList();
        return results.stream()
                .map(row -> new FocusTimeResponse(
                        (String) row[0],
                        Optional.ofNullable(row[1]).map(Number.class::cast).map(Number::longValue).orElse(0L)
                )).toList();
    }

    @Override
    public List<BudgetSpentResponse> retrieveTransactionMonthlySpentSummary(String authHeader) {
        final UserDto userDto = this.userUtil.getCurrentUserDto(authHeader);

        String totalSpentAmountQuery = """
                WITH user_budget AS (
                  SELECT id AS user_id, set_amount
                  FROM users
                  WHERE id = ?
                ),
                monthly_expenses AS (
                  SELECT
                    user_id,
                    SUM(amount) AS total_expense
                  FROM transaction
                  WHERE user_id = ?
                    AND TO_CHAR(TO_TIMESTAMP(transaction_date), 'YYYY-MM') = TO_CHAR(CURRENT_DATE, 'YYYY-MM')
                  GROUP BY user_id
                ),
                calculated AS (
                  SELECT
                    u.user_id,
                    u.set_amount,
                    COALESCE(e.total_expense, 0) AS total_expense,
                    ROUND((COALESCE(e.total_expense, 0) / u.set_amount) * 100, 2) AS expense_percentage,
                    ROUND(100 - (COALESCE(e.total_expense, 0) / u.set_amount) * 100, 2) AS remaining_percentage
                  FROM user_budget u
                  LEFT JOIN monthly_expenses e ON u.user_id = e.user_id
                )
                SELECT
                  'spent' AS overview,
                  expense_percentage AS percentage
                FROM calculated
                UNION ALL
                SELECT
                  'remaining' AS overview,
                  remaining_percentage AS percentage
                FROM calculated;
                
                """;

        Query query = entityManager.createNativeQuery(totalSpentAmountQuery);
        query.setParameter(1, userDto.getId());
        query.setParameter(2, userDto.getId());

        @SuppressWarnings("unchecked")
        List<Object[]> results = query.getResultList();
        return results.stream()
                .map(row -> new BudgetSpentResponse(
                        (String) row[0],
                        Optional.ofNullable(row[1]).map(Number.class::cast).map(Number::floatValue).orElse(0.0f)
                )).toList();
    }

    @Override
    public List<TaskCompleteResponse> retrieveTaskCompleteSummary(String authHeader) {
        final UserDto userDto = this.userUtil.getCurrentUserDto(authHeader);
        System.out.println("User Id : " + userDto.getId());

        String totalTaskCompleteQuery = """
                WITH filtered_todos AS (
                          SELECT
                            CASE
                              WHEN status = 4 THEN 'completed'
                              WHEN status IN (1, 2, 3) THEN 'active'
                            END AS status_group
                          FROM todo_list
                          WHERE user_id = ?
                            AND status IN (1, 2, 3, 4)
                        ),
                        status_counts AS (
                          SELECT
                            status_group,
                            COUNT(*) AS count
                          FROM filtered_todos
                          GROUP BY status_group
                        ),
                        total_count AS (
                          SELECT
                            SUM(count) AS total
                          FROM status_counts
                        )
                        SELECT
                          sc.status_group,
                          ROUND((CAST(sc.count AS DECIMAL) / tc.total) * 100, 2) AS percentage
                        FROM status_counts sc, total_count tc;
            """;


        Query query = entityManager.createNativeQuery(totalTaskCompleteQuery);
        query.setParameter(1, userDto.getId());

        @SuppressWarnings("unchecked")
        List<Object[]> results = query.getResultList();
        return results.stream()
                .map(row -> new TaskCompleteResponse(
                        (String) row[0],
                        Optional.ofNullable(row[1]).map(Number.class::cast).map(Number::floatValue).orElse(0.0f)
                )).toList();
    }



}
