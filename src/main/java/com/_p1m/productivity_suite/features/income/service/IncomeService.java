package com._p1m.productivity_suite.features.income.service;

import com._p1m.productivity_suite.features.income.dto.IncomeRequest;
import com._p1m.productivity_suite.features.income.dto.IncomeResponse;

import java.util.List;

public interface IncomeService {
    void createIncome(final String authHeader, final IncomeRequest incomeRequest);
    List<IncomeResponse> retrieveAll(final String authHeader);
    IncomeResponse retrieveOne(final Long id);
    void updateIncome(final Long id, final IncomeRequest incomeRequest);
    void deleteIncome(final Long id);
}
