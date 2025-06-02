package com._p1m.productivity_suite.features.summary.service.impl;

import com._p1m.productivity_suite.features.summary.dto.FocusTimeResponse;
import com._p1m.productivity_suite.features.summary.service.SummaryService;
import com._p1m.productivity_suite.features.users.dto.response.UserDto;
import com._p1m.productivity_suite.features.users.utils.UserUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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
//
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


        // TypedQuery<Object[]> query = entityManager.createNativeQuery(totalFocusTimeQuery, Object[].class);
        Query query = entityManager.createNativeQuery(totalFocusTimeQuery);
        query.setParameter(1, userDto.getId());

        @SuppressWarnings("unchecked")
        List<Object[]> results = query.getResultList();
        return results.stream()
                .map(row -> new FocusTimeResponse(
                        (String) row[0],
                        ((Number) row[1]).longValue()
                )).toList();
    }


}
