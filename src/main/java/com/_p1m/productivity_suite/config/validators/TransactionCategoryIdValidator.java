package com._p1m.productivity_suite.config.validators;

import com._p1m.productivity_suite.config.annotations.ValidCategoryId;
import com._p1m.productivity_suite.features.categories.repository.CategoryRepository;
import com._p1m.productivity_suite.features.users.utils.UserUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TransactionCategoryIdValidator implements ConstraintValidator<ValidCategoryId, Long> {

    private final CategoryRepository categoryRepository;
    private final UserUtil userUtil;

    @Override
    public boolean isValid(Long categoryId, ConstraintValidatorContext context) {
        if (categoryId == null) {
            buildViolation(context, "Category ID is required.");
            return false;
        }
        Long userId = userUtil.getCurrentUserInternal().getId();
        boolean exists = categoryRepository.existsByIdAndUserId(categoryId, userId);

        if (!exists) {
            buildViolation(context, "Category not found or does not belong to the user.");
            return false;
        }
        return true;
    }

    private void buildViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
    }
}
