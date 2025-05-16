package com._p1m.productivity_suite.features.categories.service;

import com._p1m.productivity_suite.features.categories.dto.CategoryRequest;
import com._p1m.productivity_suite.features.categories.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {
    void createCategory(final String authHeader, final CategoryRequest categoryRequest);
    List<CategoryResponse> retrieveAll(final String authHeader);
    CategoryResponse retrieveOne(final Long id);
    void updateCategory(final Long id, final CategoryRequest categoryRequest);
    void deleteCategory(final Long id);
    void updateStatus(final Long id, final boolean active);
}
