package com._p1m.productivity_suite.features.categories.service.impl;

import com._p1m.productivity_suite.data.models.Category;
import com._p1m.productivity_suite.features.categories.dto.CategoryRequest;
import com._p1m.productivity_suite.features.categories.dto.CategoryResponse;
import com._p1m.productivity_suite.features.categories.repository.CategoryRepository;
import com._p1m.productivity_suite.features.categories.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static com._p1m.productivity_suite.config.utils.EntityServiceHelper.*;
import static com._p1m.productivity_suite.config.utils.PersistenceUtils.*;
import static com._p1m.productivity_suite.config.utils.RepositoryUtils.*;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public void createCategory(final CategoryRequest request) {
        final Category category = new Category(request.getName());
        save(this.categoryRepository, category, "Category");
    }

    @Override
    public List<CategoryResponse> retrieveAll() {
        final Sort sortByName = Sort.by(Sort.Direction.ASC, "name");
        final List<Category> categories = findAll(this.categoryRepository, sortByName);
        return mapList(categories, CategoryResponse.class, this.modelMapper);
    }

    @Override
    public CategoryResponse retrieveOne(final Long id) {
        final Category category = findByIdOrThrow(this.categoryRepository, id, "Category");
        return map(category, CategoryResponse.class, this.modelMapper);
    }

    @Override
    public void updateCategory(final Long id, final CategoryRequest request) {
        final Category category = findByIdOrThrow(this.categoryRepository, id, "Category");
        category.setName(request.getName());
        save(this.categoryRepository, category, "Category");
    }

    @Override
    public void deleteCategory(final Long id) {
        deleteById(this.categoryRepository, id, "Category");
    }

    @Override
    public void updateStatus(final Long id, final boolean active) {
        final Category category = findByIdOrThrow(this.categoryRepository, id, "Category");
        category.setActive(active);
        save(this.categoryRepository, category, "Category");
    }
}
