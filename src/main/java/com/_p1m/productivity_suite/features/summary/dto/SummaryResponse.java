package com._p1m.productivity_suite.features.summary.dto;

import java.util.List;

public record SummaryResponse(
        List<FocusTimeResponse> focusTime,
        List<BudgetSpentResponse> spentAmount,
        List<TaskCompleteResponse> taskComplete

) {}
