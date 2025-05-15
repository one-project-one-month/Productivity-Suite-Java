package com._p1m.productivity_suite.features.categories.service.impl;

import com._p1m.productivity_suite.config.exceptions.UnauthorizedException;
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
import java.util.Optional;

import static com._p1m.productivity_suite.config.utils.EntityServiceHelper.*;
import static com._p1m.productivity_suite.config.utils.PersistenceUtils.*;
import static com._p1m.productivity_suite.config.utils.RepositoryUtils.*;
import static com._p1m.productivity_suite.config.utils.AuthorizationUtils.*;

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

        final User user = findByIdOrThrow(this.userRepository, userDto.getId(), "User");

        final Category category = new Category(
                request.getName(),
                request.getType(),
                user
        );
        save(this.categoryRepository, category, "Category");
    }

    @Override
    public List<CategoryResponse> retrieveAll(final String authHeader) {
        final UserDto userDto = this.userUtil.getCurrentUserDto(authHeader);
        final Sort sortByName = Sort.by(Sort.Direction.ASC, "name");
        final List<Category> categories = findAllByUserId(userDto.getId(), sortByName, this.categoryRepository::findAllByUserId);
        return categories.stream()
                .map(this::toCategoryResponseWithType)
                .toList();
    }

    @Override
    public CategoryResponse retrieveOne(final String authHeader, final Long id) {
        final UserDto userDto = this.userUtil.getCurrentUserDto(authHeader);
        final Category category = findByIdOrThrow(this.categoryRepository, id, "Category");
        checkUserAuthorization(userDto, category, (entity, user) -> entity.getUser().getId());
        return toCategoryResponseWithType(category);
    }

    @Override
    public void updateCategory(final String authHeader, final Long id, final CategoryRequest request) {
        final UserDto userDto = this.userUtil.getCurrentUserDto(authHeader);
        final Category category = findByIdOrThrow(this.categoryRepository, id, "Category");
        checkUserAuthorization(userDto, category, (entity, user) -> entity.getUser().getId());
        category.setName(request.getName());
        category.setType(request.getType());
        save(this.categoryRepository, category, "Category");
    }

    @Override
    public void deleteCategory(final String authHeader, final Long id) {
        final UserDto userDto = this.userUtil.getCurrentUserDto(authHeader);
        final Category category = findByIdOrThrow(this.categoryRepository, id, "Category");
        checkUserAuthorization(userDto, category, (entity, user) -> entity.getUser().getId());
        deleteById(this.categoryRepository, id, "Category");
    }

    @Override
    public void updateStatus(final String authHeader, final Long id, final boolean active) {
        final UserDto userDto = this.userUtil.getCurrentUserDto(authHeader);
        final Category category = findByIdOrThrow(this.categoryRepository, id, "Category");
        checkUserAuthorization(userDto, category, (entity, user) -> entity.getUser().getId());
        category.setActive(active);
        save(this.categoryRepository, category, "Category");
    }

    private CategoryResponse toCategoryResponseWithType(final Category category) {
        final CategoryResponse response = map(category, CategoryResponse.class, this.modelMapper);
        response.setTypeCode(category.getType());
        response.setTypeValue(CategoryType.fromInt(category.getType()).getCode());
        return response;
    }
}
