package com._p1m.productivity_suite.features.currency.controller;

import com._p1m.productivity_suite.config.response.dto.ApiResponse;
import com._p1m.productivity_suite.data.models.Currency;
import com._p1m.productivity_suite.features.currency.service.CurrencyService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class currencyController {

//    @Autowired
//    CurrencyService currencyService;
//
//    @PostMapping
//    @Operation(
//            summary = "Create a new Currency",
//            description = "Creates a new currency with provided details.",
//            responses = {
//                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",description = "Currency created successfully...",
//                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
//            }
//    )
//    public ResponseEntity<ApiResponse> createCurrency(@Validated @RequestBody final Currency currency){
//        return currencyService.createCurrency(currency);
//    }
}
