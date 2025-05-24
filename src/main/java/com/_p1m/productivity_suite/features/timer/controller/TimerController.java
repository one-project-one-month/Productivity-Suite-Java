package com._p1m.productivity_suite.features.timer.controller;

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
import com._p1m.productivity_suite.features.categories.dto.CategoryRequest;
import com._p1m.productivity_suite.features.timer.service.TimerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Tag(name = "Timer Module", description = "Endpoints for Pomodoro Timer")
@RestController
@RequestMapping("/productivity-suite/api/v1/timers")
@RequiredArgsConstructor
public class TimerController {
	private final TimerService timerService;
	
//	@PostMapping
//    @Operation(
//            summary = "Create a new timer",
//            description = "Creates a new timer with the provided details.",
//            responses = {
//                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "timer created successfully",
//                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
//            }
//    )
//    public ResponseEntity<ApiResponse> createCategory(
//            @Validated @RequestBody final CategoryRequest categoryRequest,
//            final HttpServletRequest request,
//            @RequestHeader(value = "Authorization") final String authHeader
//    ) {
//        final double requestStartTime = RequestUtils.extractRequestStartTime(request);
//
//        this.timerService.createCategory(authHeader, categoryRequest);
//
//        final ApiResponse response = ApiResponse.builder()
//                .success(1)
//                .code(200)
//                .data(true)
//                .message("Category created successfully")
//                .build();
//        return ResponseUtils.buildResponse(request, response, requestStartTime);
//    }
}
