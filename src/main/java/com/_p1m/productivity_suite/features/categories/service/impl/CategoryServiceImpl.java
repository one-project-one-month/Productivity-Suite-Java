package com._p1m.productivity_suite.features.categories.service.impl;

import com._p1m.productivity_suite.data.enums.CategoryType;
import com._p1m.productivity_suite.data.models.Category;
import com._p1m.productivity_suite.data.models.User;
import com._p1m.productivity_suite.features.categories.dto.CategoryRequest;
import com._p1m.productivity_suite.features.categories.dto.CategoryResponse;
import com._p1m.productivity_suite.features.categories.repository.CategoryRepository;
import com._p1m.productivity_suite.features.categories.service.CategoryService;
import com._p1m.productivity_suite.features.users.dto.response.UserDto;
import com._p1m.productivity_suite.features.users.repository.UserRepository;
import com._p1m.productivity_suite.features.users.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import com._p1m.productivity_suite.config.utils.PersistenceUtils;
import com._p1m.productivity_suite.config.utils.RepositoryUtils;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final UserUtil userUtil;
    private final UserRepository userRepository;

    @Override
    public void createCategory(final String authHeader, final CategoryRequest request) {
        final UserDto userDto = this.userUtil.getCurrentUserDto(authHeader);

        final User user = RepositoryUtils.findByIdOrThrow(this.userRepository, userDto.getId(), "User");

        final Category category = new Category(
                request.name(),
                request.description(),
                request.type(),
                user
        );
        PersistenceUtils.save(this.categoryRepository, category, "Category");
    }

    @Override
    public List<CategoryResponse> retrieveAll(final String authHeader) {
        final UserDto userDto = this.userUtil.getCurrentUserDto(authHeader);
        final Sort sortByName = Sort.by(Sort.Direction.ASC, "name");
        final List<Category> categories = RepositoryUtils.findAllByUserId(userDto.getId(), sortByName, this.categoryRepository::findAllByUserId);
        return categories.stream()
                .map(this::toCategoryResponseWithType)
                .toList();
    }

    @Override
    public CategoryResponse retrieveOne(final Long id) {
        final Category category = RepositoryUtils.findByIdOrThrow(this.categoryRepository, id, "Category");
        return this.toCategoryResponseWithType(category);
    }

    @Override
    public void updateCategory(final Long id, final CategoryRequest request) {
        final Category category = RepositoryUtils.findByIdOrThrow(this.categoryRepository, id, "Category");
        category.setName(request.name());
        category.setDescription(request.description());
        category.setType(request.type());
        PersistenceUtils.save(this.categoryRepository, category, "Category");
    }

    @Override
    public void deleteCategory(final Long id) {
        RepositoryUtils.findByIdOrThrow(this.categoryRepository, id, "Category");
        PersistenceUtils.deleteById(this.categoryRepository, id, "Category");
    }

    @Override
    public void updateStatus(final Long id, final boolean active) {
        final Category category = RepositoryUtils.findByIdOrThrow(this.categoryRepository, id, "Category");
        category.setActive(active);
        PersistenceUtils.save(this.categoryRepository, category, "Category");
    }

    private CategoryResponse toCategoryResponseWithType(final Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.isActive(),
                category.getType(),
                CategoryType.fromInt(category.getType()).getCode(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }
}
