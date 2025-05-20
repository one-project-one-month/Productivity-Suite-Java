package com._p1m.productivity_suite.features.todolist.controller;

import com._p1m.productivity_suite.config.annotations.AuthorizationCheck;
import com._p1m.productivity_suite.config.request.RequestUtils;
import com._p1m.productivity_suite.config.response.dto.ApiResponse;
import com._p1m.productivity_suite.config.response.utils.ResponseUtils;
import com._p1m.productivity_suite.features.todolist.dto.*;
import com._p1m.productivity_suite.features.todolist.service.TodoListService;
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

@Tag(name = "Todo-List Module", description = "Endpoints for todo-list management")
@RestController
@RequestMapping("/productivity-suite/api/v1/todo-lists")
@RequiredArgsConstructor
public class TodoListController {

    private final TodoListService todoListService;

    @PostMapping
    @Operation(
            summary = "Create a new todo-list",
            description = "Creates a new todo-list item for current user.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.
                            ApiResponse(responseCode = "200",
                            description = "Todo-list successfully created!",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    public ResponseEntity<ApiResponse> createTodoList(
            @Validated @RequestBody final TodoListRequest todoListRequest,
            @RequestHeader("Authorization") final String authHeader,
            final HttpServletRequest request
    ) {
        final double start = RequestUtils.extractRequestStartTime(request);

        this.todoListService.createTodoList(authHeader, todoListRequest);

        final ApiResponse res = ApiResponse.builder()
                .success(1).code(200).data(true)
                .message("Todo-list created successfully!")
                .build();
        return ResponseUtils.buildResponse(request, res, start);
    }

    @GetMapping
    @Operation(
            summary = "Retrieve all todo-lists.",
            description = "Fetches all todo-list items for the current user.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Todo-list retrieved successfully!",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    public ResponseEntity<ApiResponse> retrieveAllTodoLists(
            @RequestHeader("Authorization") final String authHeader,
            final HttpServletRequest request
    ) {
        final double start = RequestUtils.extractRequestStartTime(request);

        final List<TodoListResponse> todoLists = this.todoListService.retrieveAll(authHeader);

        final ApiResponse res = ApiResponse.builder()
                .success(1).code(200).data(true)
                .message("Todo-lists retrieved successfully!")
                .data(todoLists)
                .build();
        return ResponseUtils.buildResponse(request, res, start);
    }

    @AuthorizationCheck(resource = "TODOLIST", idParam = "id")
    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a todo-list by ID",
            description = "Fetches the details of a specific todo-list by its ID.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Todo-list retrieved successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            })
    public ResponseEntity<ApiResponse> retrieveTodoListById(
            @PathVariable final Long id,
            final HttpServletRequest request
    ) {
        final double start = RequestUtils.extractRequestStartTime(request);

        final TodoListResponse todoList = todoListService.retrieveOne(id);

        ApiResponse res = ApiResponse.builder()
                .success(1).code(200).data(true)
                .message("Todo-list retrieved successfully")
                .data(todoList)
                .build();
        return ResponseUtils.buildResponse(request, res, start);
    }

    @AuthorizationCheck(resource = "TODOLIST", idParam = "id")
    @PutMapping("/{id}")
    @Operation(summary = "Update a todo-list",
            description = "Update the details of an existing todo-list by its ID.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Todo-list updated successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    public ResponseEntity<ApiResponse> updateTodoList(
            @PathVariable final Long id,
            @Validated @RequestBody final TodoListRequest todoListRequest,
            final HttpServletRequest request
    ) {
        final double start = RequestUtils.extractRequestStartTime(request);

        todoListService.updateTodoList(id, todoListRequest);

        ApiResponse res = ApiResponse.builder()
                .success(1).code(200).data(true)
                .message("Todo-list updated successfully")
                .build();
        return ResponseUtils.buildResponse(request, res, start);
    }


    @AuthorizationCheck(resource = "TODOLIST", idParam = "id")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a todo-list",
            description = "Delete a todo-list by its ID.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Todo-list deleted successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    public ResponseEntity<ApiResponse> deleteTodoList(
            @PathVariable final Long id,
            final HttpServletRequest request
    ) {
        final double start = RequestUtils.extractRequestStartTime(request);

        todoListService.deleteTodoList(id);

        ApiResponse res = ApiResponse.builder()
                .success(1).code(200).data(true)
                .message("Todo-list deleted successfully")
                .build();
        return ResponseUtils.buildResponse(request, res, start);
    }
}
