package com._p1m.productivity_suite.features.summary.controller;


import com._p1m.productivity_suite.config.request.RequestUtils;
import com._p1m.productivity_suite.config.response.dto.ApiResponse;
import com._p1m.productivity_suite.config.response.utils.ResponseUtils;
import com._p1m.productivity_suite.features.summary.dto.BudgetSpentResponse;
import com._p1m.productivity_suite.features.summary.dto.FocusTimeResponse;
import com._p1m.productivity_suite.features.summary.dto.SummaryResponse;
import com._p1m.productivity_suite.features.summary.dto.TaskCompleteResponse;
import com._p1m.productivity_suite.features.summary.service.SummaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Summary Module", description = "Endpoints for summary management")
@RestController
@RequestMapping("/productivity-suite/api/v1/summary")
@RequiredArgsConstructor
public class SummaryController {

    private final SummaryService summaryService;

    @GetMapping
    @Operation(
            summary = "Combined Summary for total focus time, spent amount and task completion",
            description = "Retrieve for pomodoro focus time and transaction spent amount and todolist task completion for the authenticated user.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Combined summary retrieved successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    public ResponseEntity<ApiResponse> retrieveCombinedSummary(
            final HttpServletRequest request,
            @RequestHeader(value = "Authorization") final String authHeader) {

        final double requestStartTime = RequestUtils.extractRequestStartTime(request);

        final List<FocusTimeResponse> totalFocusTime = this.summaryService.retrievePomodoroWeeklyFocusTimeSummary(authHeader);
        final List<BudgetSpentResponse> totalBudgetSpent = this.summaryService.retrieveTransactionMonthlySpentSummary(authHeader);
        final List<TaskCompleteResponse> totalTaskComplete = this.summaryService.retrieveTaskCompleteSummary(authHeader);

        SummaryResponse combinedSummary = new SummaryResponse(totalFocusTime, totalBudgetSpent, totalTaskComplete);

        final ApiResponse response = ApiResponse.builder()
                .success(1)
                .code(200)
                .data(combinedSummary)
                .message("Combined summary retrieved successfully")
                .build();

        return ResponseUtils.buildResponse(request, response, requestStartTime);
    }

}
