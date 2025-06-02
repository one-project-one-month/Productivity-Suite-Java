package com._p1m.productivity_suite.features.summary.service;

import com._p1m.productivity_suite.features.summary.dto.FocusTimeResponse;

import java.util.List;

public interface SummaryService {

    List<FocusTimeResponse> retrievePomodoroWeeklyFocusTimeSummary(final String authHeader);

}
