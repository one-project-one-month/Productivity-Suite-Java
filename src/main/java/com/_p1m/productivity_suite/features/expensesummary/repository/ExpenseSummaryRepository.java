package com._p1m.productivity_suite.features.expensesummary.repository;

import java.util.List;
import java.util.Map;

public interface ExpenseSummaryRepository {
    List<Map<String, Object>> findDailySummaryByUser(Long userId);
}
