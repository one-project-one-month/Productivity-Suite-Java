package com._p1m.productivity_suite.features.income.controller;

import com._p1m.productivity_suite.config.annotations.AuthorizationCheck;
import com._p1m.productivity_suite.config.response.dto.ApiResponse;
import com._p1m.productivity_suite.config.response.utils.ResponseUtils;
import com._p1m.productivity_suite.features.income.dto.IncomeRequest;
import com._p1m.productivity_suite.features.income.dto.IncomeResponse;
import com._p1m.productivity_suite.features.income.service.IncomeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Income Module", description = "Endpoints for income management")
@RestController
@RequestMapping("/productivity-suite/api/v1/incomes")
@RequiredArgsConstructor
public class IncomeController {

    private final IncomeService incomeService;

    @PostMapping
    @Operation(
            summary = "Create a new income",
            description = "Creates a new income entry for the authenticated user.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Income created successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    public ResponseEntity<ApiResponse> createIncome(
            @Validated @RequestBody final IncomeRequest incomeRequest,
            final HttpServletRequest request,
            @RequestHeader("Authorization") final String authHeader) {
        final double requestStartTime = System.currentTimeMillis();
        this.incomeService.createIncome(authHeader, incomeRequest);

        final ApiResponse response = ApiResponse.builder()
                .success(1)
                .code(200)
                .data(true)
                .message("Income created successfully")
                .build();
        return ResponseUtils.buildResponse(request, response, requestStartTime);
    }

    @GetMapping
    @Operation(
            summary = "Retrieve all incomes",
            description = "Fetches a list of all income entries for the authenticated user.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Incomes retrieved successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    public ResponseEntity<ApiResponse> retrieveAllIncomes(
            final HttpServletRequest request,
            @RequestHeader("Authorization") final String authHeader) {
        final double requestStartTime = System.currentTimeMillis();
        final List<IncomeResponse> incomes = this.incomeService.retrieveAll(authHeader);

        final ApiResponse response = ApiResponse.builder()
                .success(1)
                .code(200)
                .data(incomes)
                .message("Incomes retrieved successfully")
                .build();
        return ResponseUtils.buildResponse(request, response, requestStartTime);
    }

    @AuthorizationCheck(resource = "INCOME", idParam = "id")
    @GetMapping("/{id}")
    @Operation(
            summary = "Retrieve an income by ID",
            description = "Fetches the details of a specific income by its ID.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Income retrieved successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    public ResponseEntity<ApiResponse> retrieveIncomeById(
            @PathVariable final Long id,
            final HttpServletRequest request) {
        final double requestStartTime = System.currentTimeMillis();
        final IncomeResponse income = this.incomeService.retrieveOne(id);

        final ApiResponse response = ApiResponse.builder()
                .success(1)
                .code(200)
                .data(income)
                .message("Income retrieved successfully")
                .build();
        return ResponseUtils.buildResponse(request, response, requestStartTime);
    }

    @AuthorizationCheck(resource = "INCOME", idParam = "id")
    @PutMapping("/{id}")
    @Operation(
            summary = "Update an income",
            description = "Updates an existing income with the provided details.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Income updated successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    public ResponseEntity<ApiResponse> updateIncome(
            @PathVariable final Long id,
            @Validated @RequestBody final IncomeRequest incomeRequest,
            final HttpServletRequest request) {
        final double requestStartTime = System.currentTimeMillis();
        this.incomeService.updateIncome(id, incomeRequest);

        final ApiResponse response = ApiResponse.builder()
                .success(1)
                .code(200)
                .data(true)
                .message("Income updated successfully")
                .build();
        return ResponseUtils.buildResponse(request, response, requestStartTime);
    }

    @AuthorizationCheck(resource = "INCOME", idParam = "id")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete an income",
            description = "Deletes an income by its ID.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Income deleted successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    public ResponseEntity<ApiResponse> deleteIncome(
            @PathVariable final Long id,
            final HttpServletRequest request) {
        final double requestStartTime = System.currentTimeMillis();
        this.incomeService.deleteIncome(id);

        final ApiResponse response = ApiResponse.builder()
                .success(1)
                .code(200)
                .data(true)
                .message("Income deleted successfully")
                .build();
        return ResponseUtils.buildResponse(request, response, requestStartTime);
    }
}
