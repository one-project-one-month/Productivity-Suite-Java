package com._p1m.productivity_suite.features.expensesummary.dto;

import java.util.List;

public record CategoryAndCurrencyResponse(
        List<CategoryDataForExpenseSummary> categories,
        List<CurrencyDataForExpenseSummary> currencies
) {}
