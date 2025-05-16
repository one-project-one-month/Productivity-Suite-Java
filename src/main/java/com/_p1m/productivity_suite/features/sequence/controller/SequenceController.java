package com._p1m.productivity_suite.features.sequence.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com._p1m.productivity_suite.config.request.RequestUtils;
import com._p1m.productivity_suite.config.response.dto.ApiResponse;
import com._p1m.productivity_suite.config.response.utils.ResponseUtils;
import com._p1m.productivity_suite.features.sequence.dto.SequenceRequest;
import com._p1m.productivity_suite.features.sequence.service.SequenceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Tag(name = "Sequence Module", description = "Endpoints for sequence management")
@RestController
@RequestMapping("/productivity-suite/api/v1/sequences")
@RequiredArgsConstructor
public class SequenceController {
	private final SequenceService sequenceService;
	@PostMapping
    @Operation(
            summary = "Create a new sequence",
            description = "Creates a new sequence with the provided details.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Sequence created successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    public ResponseEntity<ApiResponse> createCategory(
            @Validated @RequestBody final SequenceRequest sequenceRequest,
            final HttpServletRequest request,
            @RequestHeader(value = "Authorization") final String authHeader
    ) {
        final double requestStartTime = RequestUtils.extractRequestStartTime(request);

        this.sequenceService.createSequence(authHeader, sequenceRequest);

        final ApiResponse response = ApiResponse.builder()
                .success(1)
                .code(200)
                .data(true)
                .message("Sequence created successfully")
                .build();
        return ResponseUtils.buildResponse(request, response, requestStartTime);
    }
}
