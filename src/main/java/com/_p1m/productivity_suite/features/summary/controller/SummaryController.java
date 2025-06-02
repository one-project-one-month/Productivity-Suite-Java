package com._p1m.productivity_suite.features.summary.controller;


import com._p1m.productivity_suite.config.response.dto.ApiResponse;
import com._p1m.productivity_suite.config.response.utils.ResponseUtils;
import com._p1m.productivity_suite.features.summary.dto.FocusTimeResponse;
import com._p1m.productivity_suite.features.summary.service.SummaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Summary Module", description = "Endpoints for summary management")
@RestController
@RequestMapping("/productivity-suite/api/v1/summary")
@RequiredArgsConstructor
public class SummaryController {

    private final SummaryService summaryService;

    @PostMapping
    @Operation(
            summary = "Pomodoro Timer Focus Time Summary",
            description = "Retrive for total focus time for pomodoro timer of the authenticated user.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Retrieve total focus time for pomodoro timer successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    public ResponseEntity<ApiResponse> retrieveTotalFocusTime(
            final HttpServletRequest request,
            @RequestHeader(value = "Authorization") final String authHeader) {

        final double requestStartTime = System.currentTimeMillis();
        final List<FocusTimeResponse> totalFocusTime = this.summaryService.retrievePomodoroWeeklyFocusTimeSummary(authHeader);
        final ApiResponse response = ApiResponse.builder()
                .success(1)
                .code(200)
                .data(totalFocusTime)
                .message("Total focus time for pomodoro timer retrieved successfully")
                .build();

        return ResponseUtils.buildResponse(request, response, requestStartTime);

    }
}
