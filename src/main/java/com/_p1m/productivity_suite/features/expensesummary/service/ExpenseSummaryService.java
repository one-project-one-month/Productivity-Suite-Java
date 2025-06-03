package com._p1m.productivity_suite.features.expensesummary.service;

import com._p1m.productivity_suite.features.expensesummary.dto.CategoryAndCurrencyResponse;

public interface ExpenseSummaryService {
    CategoryAndCurrencyResponse retrieveCategoryAndCurrency(String authHeader);
}
