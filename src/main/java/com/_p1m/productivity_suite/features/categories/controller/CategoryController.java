package com._p1m.productivity_suite.features.categories.controller;

import com._p1m.productivity_suite.config.annotations.AuthorizationCheck;
import com._p1m.productivity_suite.config.request.RequestUtils;
import com._p1m.productivity_suite.features.categories.dto.CategoryRequest;
import com._p1m.productivity_suite.features.categories.dto.CategoryResponse;
import com._p1m.productivity_suite.features.categories.dto.CategoryStatusUpdateRequest;
import com._p1m.productivity_suite.features.categories.service.CategoryService;
import com._p1m.productivity_suite.config.response.dto.ApiResponse;
import com._p1m.productivity_suite.config.response.utils.ResponseUtils;
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

@Tag(name = "Category Module", description = "Endpoints for category management")
@RestController
@RequestMapping("/productivity-suite/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @Operation(
            summary = "Create a new category",
            description = "Creates a new category with the provided details.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Category created successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    public ResponseEntity<ApiResponse> createCategory(
            @Validated @RequestBody final CategoryRequest categoryRequest,
            final HttpServletRequest request,
            @RequestHeader(value = "Authorization") final String authHeader
    ) {
        final double requestStartTime = RequestUtils.extractRequestStartTime(request);

        this.categoryService.createCategory(authHeader, categoryRequest);

        final ApiResponse response = ApiResponse.builder()
                .success(1)
                .code(200)
                .data(true)
                .message("Category created successfully")
                .build();
        return ResponseUtils.buildResponse(request, response, requestStartTime);
    }

    @GetMapping
    @Operation(
            summary = "Retrieve all categories",
            description = "Fetches a list of all categories.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Categories retrieved successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    public ResponseEntity<ApiResponse> retrieveAllCategories(
            final HttpServletRequest request,
            @RequestHeader(value = "Authorization") final String authHeader
    ) {
        final double requestStartTime = RequestUtils.extractRequestStartTime(request);

        final List<CategoryResponse> categories = this.categoryService.retrieveAll(authHeader);

        final ApiResponse response = ApiResponse.builder()
                .success(1)
                .code(200)
                .data(true)
                .message("Categories retrieved successfully")
                .data(categories)
                .build();
        return ResponseUtils.buildResponse(request, response, requestStartTime);
    }

    @AuthorizationCheck(resource = "CATEGORY", idParam = "id")
    @GetMapping("/{id}")
    @Operation(
            summary = "Retrieve a category by ID",
            description = "Fetches the details of a specific category by its ID.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Category retrieved successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    public ResponseEntity<ApiResponse> retrieveCategoryById(
            @PathVariable final Long id,
            final HttpServletRequest request
    ) {
        final double requestStartTime = RequestUtils.extractRequestStartTime(request);

        final CategoryResponse category = this.categoryService.retrieveOne(id);

        final ApiResponse response = ApiResponse.builder()
                .success(1)
                .code(200)
                .data(true)
                .message("Category retrieved successfully")
                .data(category)
                .build();
        return ResponseUtils.buildResponse(request, response, requestStartTime);
    }

    @AuthorizationCheck(resource = "CATEGORY", idParam = "id")
    @PutMapping("/{id}")
    @Operation(
            summary = "Update a category",
            description = "Updates an existing category with the provided details.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Category updated successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    public ResponseEntity<ApiResponse> updateCategory(
            @PathVariable final Long id,
            @Validated @RequestBody final CategoryRequest categoryRequest,
            final HttpServletRequest request
    ) {
        final double requestStartTime = RequestUtils.extractRequestStartTime(request);

        this.categoryService.updateCategory(id, categoryRequest);

        final ApiResponse response = ApiResponse.builder()
                .success(1)
                .code(200)
                .data(true)
                .message("Category updated successfully")
                .build();
        return ResponseUtils.buildResponse(request, response, requestStartTime);
    }

    @AuthorizationCheck(resource = "CATEGORY", idParam = "id")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a category",
            description = "Deletes a category by its ID.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Category deleted successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    public ResponseEntity<ApiResponse> deleteCategory(
            @PathVariable final Long id,
            final HttpServletRequest request
    ) {
        final double requestStartTime = RequestUtils.extractRequestStartTime(request);

        this.categoryService.deleteCategory(id);

        final ApiResponse response = ApiResponse.builder()
                .success(1)
                .code(200)
                .data(true)
                .message("Category deleted successfully")
                .build();
        return ResponseUtils.buildResponse(request, response, requestStartTime);
    }

    @AuthorizationCheck(resource = "CATEGORY", idParam = "id")
    @PatchMapping("/{id}/status")
    @Operation(
            summary = "Update category status",
            description = "Enables or disables the category by updating its active status.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Status updated successfully",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
            }
    )
    public ResponseEntity<ApiResponse> updateCategoryStatus(
            @PathVariable final Long id,
            @RequestBody final CategoryStatusUpdateRequest statusRequest,
            final HttpServletRequest request
    ) {
        final double requestStartTime = RequestUtils.extractRequestStartTime(request);

        this.categoryService.updateStatus(id, statusRequest.isActive());

        final ApiResponse response = ApiResponse.builder()
                .success(1)
                .code(200)
                .data(true)
                .message("Category status updated successfully")
                .build();
        return ResponseUtils.buildResponse(request, response, requestStartTime);
    }

}
