package com._p1m.productivity_suite.features.currency.controller;

import com._p1m.productivity_suite.config.response.dto.ApiResponse;
import com._p1m.productivity_suite.config.response.utils.ResponseUtils;
import com._p1m.productivity_suite.data.models.Currency;
import com._p1m.productivity_suite.features.currency.dto.CurrencyRequest;
import com._p1m.productivity_suite.features.currency.service.CurrencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class currencyController {

    @Autowired
    CurrencyService currencyService;

    @PostMapping
    @Operation(
            summary = "Create a new Currency",
            description = "Creates a new currency with provided details.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "Currency created successfully...",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    public ResponseEntity<ApiResponse> createCurrency(@Validated @RequestBody final CurrencyRequest currencyRequest,
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
        return ResponseUtils.buildResponse(request,response,requestStartTime);
    }
}
