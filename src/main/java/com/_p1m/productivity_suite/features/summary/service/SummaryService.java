package com._p1m.productivity_suite.features.summary.service;

import com._p1m.productivity_suite.features.summary.dto.BudgetSpentResponse;
import com._p1m.productivity_suite.features.summary.dto.FocusTimeResponse;
import com._p1m.productivity_suite.features.summary.dto.TaskCompleteResponse;

import java.util.List;

public interface SummaryService {

    List<FocusTimeResponse> retrievePomodoroWeeklyFocusTimeSummary(final String authHeader);
    List<BudgetSpentResponse> retrieveTransactionMonthlySpentSummary(final String authHeader);
    List<TaskCompleteResponse> retrieveTaskCompleteSummary(final String authHeader);
}
