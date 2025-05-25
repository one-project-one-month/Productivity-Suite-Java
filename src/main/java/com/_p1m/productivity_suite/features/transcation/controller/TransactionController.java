package com._p1m.productivity_suite.features.transcation.controller;

import com._p1m.productivity_suite.config.annotations.AuthorizationCheck;
import com._p1m.productivity_suite.config.request.RequestUtils;
import com._p1m.productivity_suite.config.response.dto.ApiResponse;
import com._p1m.productivity_suite.config.response.utils.ResponseUtils;
import com._p1m.productivity_suite.features.transcation.dto.TransactionRequest;
import com._p1m.productivity_suite.features.transcation.dto.TransactionResponse;
import com._p1m.productivity_suite.features.transcation.service.impl.TransactionService;
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

@Tag(name="Transaction Module",description = "Endpoints for transaction management")
@RestController
@RequestMapping("/productivity-suite/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    @Operation(
            summary = "Create a new transaction",
            description = "Creates a new transaction with the provided details.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Transaction created successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    public ResponseEntity<ApiResponse> createTransaction(
            @Validated @RequestBody final TransactionRequest transactionRequest,
            final HttpServletRequest request,
            @RequestHeader(value = "Authorization") final String authHeader
    ) {
        final double requestStartTime = RequestUtils.extractRequestStartTime(request);

        this.transactionService.createTransaction(authHeader, transactionRequest);

        final ApiResponse response = ApiResponse.builder()
                .success(1)
                .code(200)
                .data(true)
                .message("Transaction created successfully")
                .build();
        return ResponseUtils.buildResponse(request, response, requestStartTime);
    }

    @AuthorizationCheck(resource = "TRANSACTION", idParam = "id")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a transaction",
            description = "Delete a transaction by its ID.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Transaction deleted successfully.",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    public ResponseEntity<ApiResponse> deleteTransaction(
            @PathVariable final long id,
            final HttpServletRequest request
    ) {
        final double requestStartTime = RequestUtils.extractRequestStartTime(request);
        this.transactionService.deleteTransaction(id);

        final ApiResponse response = ApiResponse.builder()
                .success(1)
                .code(200)
                .data(true)
                .message("Transaction deleted successfully")
                .build();
        return ResponseUtils.buildResponse(request, response, requestStartTime);
    }

    @AuthorizationCheck(resource = "TRANSACTION", idParam = "id")
    @PutMapping("/{id}")
    @Operation(
            summary = "Update a transaction",
            description = "Update an existing transaction with the provided details.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Transaction updated successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }

    )
    public ResponseEntity<ApiResponse> UpdateTransaction(
            @PathVariable final Long id,
            @Validated @RequestBody final TransactionRequest transactionRequest,
            final HttpServletRequest request

    ) {
        final double requestStartTime = RequestUtils.extractRequestStartTime(request);
        this.transactionService.updateTransaction(id, transactionRequest);
        final ApiResponse response = ApiResponse.builder()
                .success(1)
                .code(200)
                .data(true)
                .message("Transaction updated successfully")
                .build();

        return ResponseUtils.buildResponse(request, response, requestStartTime);

    }

    @GetMapping
    @Operation(
            summary = "Retrieve all transactions",
            description = "Fetch a list of all transactions.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Transaction retrieved successfully.",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }

    )
    public ResponseEntity<ApiResponse> getAllTransactions(
            final HttpServletRequest request,
            @RequestHeader(value = "Authorization") final String authHeader
    ) {
        final double requestStartTime = RequestUtils.extractRequestStartTime(request);

        final List<TransactionResponse> transactions = this.transactionService.retrieveAll(authHeader);

        final ApiResponse response = ApiResponse.builder()
                .success(1)
                .code(200)
                .data(true)
                .message("Transactions retrieved successfully")
                .data(transactions)
                .build();
        return ResponseUtils.buildResponse(request, response, requestStartTime);
    }

    @AuthorizationCheck(resource = "TRANSACTION", idParam = "id")
    @GetMapping("/{id}")
    @Operation(
            summary = "Retrieve a transaction by ID",
            description = "Fetches the details of a specific transaction by its ID.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Transaction retrieved successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    public ResponseEntity<ApiResponse> retrieveTransactionById(
            final HttpServletRequest request,
            @PathVariable final long id
    ) {
        final double requestStartTime = RequestUtils.extractRequestStartTime(request);
        final TransactionResponse transaction = this.transactionService.retrieveOne(id);

        final ApiResponse response = ApiResponse.builder()
                .success(1)
                .code(200)
                .data(true)
                .message("Transaction retrieved successfully")
                .data(transaction)
                .build();
        return ResponseUtils.buildResponse(request, response, requestStartTime);



    }

}
