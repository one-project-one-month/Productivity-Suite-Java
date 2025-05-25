package com._p1m.productivity_suite.features.pomodoro.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com._p1m.productivity_suite.config.annotations.AuthorizationCheck;
import com._p1m.productivity_suite.config.request.RequestUtils;
import com._p1m.productivity_suite.config.response.dto.ApiResponse;
import com._p1m.productivity_suite.config.response.utils.ResponseUtils;
import com._p1m.productivity_suite.features.pomodoro.dto.PomodoroRestResponse;
import com._p1m.productivity_suite.features.pomodoro.service.PomodoroRestService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Tag(name = "Pomodoro Module", description = "Endpoints for Pomodoro management")
@RestController
@RequestMapping("/productivity-suite/api/v1/pomodoro")
@RequiredArgsConstructor
public class PomodoroRestController {
	private final PomodoroRestService pomodoroRestService;

	@GetMapping
	@Operation(
			summary = "Retrieve active data session by user email with status false",
			description = "Fetches the details of a specific active data session by its status.", 
			responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", 
					description = "Data retrieved successfully", 
					content = @Content(schema = @Schema(implementation = ApiResponse.class))) })
	public ResponseEntity<ApiResponse> retrieveCategoryById(
			@RequestHeader(value = "Authorization") final String authHeader, final HttpServletRequest request) {
		final double requestStartTime = RequestUtils.extractRequestStartTime(request);

		final PomodoroRestResponse restResponse = this.pomodoroRestService.retrieveDataByEmailAndStatus(authHeader);

		final ApiResponse response = ApiResponse.builder().success(1).code(200).data(true)
				.message("Data retrieved successfully").data(restResponse).build();
		return ResponseUtils.buildResponse(request, response, requestStartTime);
	}

	@AuthorizationCheck(resource = "SEQUENCE", idParam = "id")
	@DeleteMapping("/{id}")
	@Operation(
			summary = "Delete sequence, and its related tables.",
			description = "Deletes sequence, and its related tables by its ID.", 
			responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", 
			description = "Data deleted successfully", content = @Content(schema = @Schema(implementation = ApiResponse.class))) })
	public ResponseEntity<ApiResponse> deletePomodoActiveData(@PathVariable("id") final Long id,
			final HttpServletRequest request) {
		final double requestStartTime = RequestUtils.extractRequestStartTime(request);

		this.pomodoroRestService.deleteData(id);

		final ApiResponse response = ApiResponse.builder().success(1).code(200).data(true)
				.message("Data deleted successfully.").build();
		return ResponseUtils.buildResponse(request, response, requestStartTime);
	}
	
}
