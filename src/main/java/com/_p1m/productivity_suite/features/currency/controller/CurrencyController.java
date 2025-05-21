package com._p1m.productivity_suite.features.currency.controller;

import com._p1m.productivity_suite.config.annotations.AuthorizationCheck;
import com._p1m.productivity_suite.config.response.dto.ApiResponse;
import com._p1m.productivity_suite.config.response.utils.ResponseUtils;
import com._p1m.productivity_suite.features.currency.dto.CurrencyRequest;
import com._p1m.productivity_suite.features.currency.dto.CurrencyResponse;
import com._p1m.productivity_suite.features.currency.service.CurrencyService;
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

@Tag(name = "Currency Module", description = "Endpoints for currency management")
@RestController
@RequestMapping("/productivity-suite/api/v1/currencies")
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;

    @PostMapping
    @Operation(
            summary = "Create a new currency",
            description = "Creates a new currency for the authenticated user.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Currency created successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    public ResponseEntity<ApiResponse> createCurrency(
            @Validated @RequestBody final CurrencyRequest currencyRequest,
            final HttpServletRequest request,
            @RequestHeader(value = "Authorization") final String authHeader) {
        final double requestStartTime = System.currentTimeMillis();
        this.currencyService.createCurrency(authHeader, currencyRequest);

        final ApiResponse response = ApiResponse.builder()
                .success(1)
                .code(200)
                .data(true)
                .message("Currency created successfully")
                .build();
        return ResponseUtils.buildResponse(request, response, requestStartTime);
    }

    @GetMapping
    @Operation(
            summary = "Retrieve all currencies",
            description = "Fetches a list of all currencies for the authenticated user.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Currencies retrieved successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    public ResponseEntity<ApiResponse> retrieveAllCurrencies(
            final HttpServletRequest request,
            @RequestHeader(value = "Authorization") final String authHeader) {
        final double requestStartTime = System.currentTimeMillis();
        final List<CurrencyResponse> currencies = this.currencyService.retrieveAll(authHeader);

        final ApiResponse response = ApiResponse.builder()
                .success(1)
                .code(200)
                .data(currencies)
                .message("Currencies retrieved successfully")
                .build();
        return ResponseUtils.buildResponse(request, response, requestStartTime);
    }

    @AuthorizationCheck(resource = "CURRENCY", idParam = "id")
    @GetMapping("/{id}")
    @Operation(
            summary = "Retrieve a currency by ID",
            description = "Fetches the details of a specific currency by its ID.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Currency retrieved successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    public ResponseEntity<ApiResponse> retrieveCurrencyById(
            @PathVariable final Long id,
            final HttpServletRequest request) {
        final double requestStartTime = System.currentTimeMillis();
        final CurrencyResponse currency = this.currencyService.retrieveOne(id);

        final ApiResponse response = ApiResponse.builder()
                .success(1)
                .code(200)
                .data(currency)
                .message("Currency retrieved successfully")
                .build();
        return ResponseUtils.buildResponse(request, response, requestStartTime);
    }

    @AuthorizationCheck(resource = "CURRENCY", idParam = "id")
    @PutMapping("/{id}")
    @Operation(
            summary = "Update a currency",
            description = "Updates an existing currency with the provided details.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Currency updated successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    public ResponseEntity<ApiResponse> updateCurrency(
            @PathVariable final Long id,
            @Validated @RequestBody final CurrencyRequest currencyRequest,
            final HttpServletRequest request
    ) {
        final double requestStartTime = System.currentTimeMillis();

        this.currencyService.updateCurrency(id, currencyRequest);

        final ApiResponse response = ApiResponse.builder()
                .success(1)
                .code(200)
                .data(true)
                .message("Currency updated successfully")
                .build();
        return ResponseUtils.buildResponse(request, response, requestStartTime);
    }

    @AuthorizationCheck(resource = "CURRENCY", idParam = "id")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a currency",
            description = "Deletes a currency by its ID.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Currency deleted successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    public ResponseEntity<ApiResponse> deleteCurrency(
            @PathVariable final Long id,
            final HttpServletRequest request
    ) {
        final double requestStartTime = System.currentTimeMillis();

        this.currencyService.deleteCurrency(id);

        final ApiResponse response = ApiResponse.builder()
                .success(1)
                .code(200)
                .data(true)
                .message("Currency deleted successfully")
                .build();
        return ResponseUtils.buildResponse(request, response, requestStartTime);
    }
}
