package com._p1m.productivity_suite.features.expensesummary.service;

import com._p1m.productivity_suite.features.expensesummary.dto.CategoryAndCurrencyResponse;

import java.util.List;
import java.util.Map;

public interface ExpenseSummaryService {
    CategoryAndCurrencyResponse retrieveCategoryAndCurrency(String authHeader);
    List<Map<String, Object>> getDailyFlatSummary(final String authHeader);
    List<Map<String, Object>> getConvertedCategorySummaryByDay(String authHeader, Long categoryId, String currencyCode);
}
