package com._p1m.productivity_suite.features.expensesummary.controller;

import com._p1m.productivity_suite.config.annotations.AuthorizationCheck;
import com._p1m.productivity_suite.config.request.RequestUtils;
import com._p1m.productivity_suite.config.response.dto.ApiResponse;
import com._p1m.productivity_suite.config.response.utils.ResponseUtils;
import com._p1m.productivity_suite.features.expensesummary.dto.CategoryAndCurrencyResponse;
import com._p1m.productivity_suite.features.expensesummary.service.ExpenseSummaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "Expense Summary Module", description = "Endpoints for expense summary and related data")
@RestController
@RequestMapping("/productivity-suite/api/v1/expense-summary")
@RequiredArgsConstructor
public class ExpenseSummaryController {

    private final ExpenseSummaryService expenseSummaryService;

    @GetMapping(value = "categories-and-currencies")
    @Operation(
            summary = "Get categories and currencies for expense summary",
            description = "Retrieves the list of available categories and currencies to be used in expense summary view.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully fetched summary data",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    public ResponseEntity<ApiResponse> retrieveCategoryAndCurrency(
            final HttpServletRequest request,
            @Parameter(hidden = true)
            @RequestHeader(value = "Authorization") final String authHeader
    ) {
        final double requestStartTime = RequestUtils.extractRequestStartTime(request);

        final CategoryAndCurrencyResponse data = this.expenseSummaryService.retrieveCategoryAndCurrency(authHeader);

        final ApiResponse response = ApiResponse.builder()
                .success(1)
                .code(200)
                .message("Expense summary data retrieved successfully")
                .data(data)
                .build();

        return ResponseUtils.buildResponse(request, response, requestStartTime);
    }

    @GetMapping("/daily-flat")
    @Operation(
            summary = "Get daily flat summary grouped by date",
            description = "Returns daily transaction totals grouped by date and flattened by category name. Response is dynamic, category names appear as keys.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Successfully fetched daily flat summary",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class))
                    )
            }
    )
    public ResponseEntity<ApiResponse> getFlatSummary(
            final HttpServletRequest request,
            @RequestHeader(value = "Authorization") final String authHeader
    ) {
        final double requestStartTime = RequestUtils.extractRequestStartTime(request);

        final List<Map<String, Object>> data = this.expenseSummaryService.getDailyFlatSummary(authHeader);

        final ApiResponse response = ApiResponse.builder()
                .success(1)
                .code(200)
                .message("Daily flat expense summary retrieved successfully")
                .data(data)
                .build();

        return ResponseUtils.buildResponse(request, response, requestStartTime);
    }
}
