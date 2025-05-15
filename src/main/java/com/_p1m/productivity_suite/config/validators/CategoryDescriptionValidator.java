package com._p1m.productivity_suite.config.validators;

import com._p1m.productivity_suite.config.annotations.ValidCategoryDescription;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CategoryDescriptionValidator implements ConstraintValidator<ValidCategoryDescription, String> {

    @Override
    public boolean isValid(final String categoryDescription, final ConstraintValidatorContext context) {
        if (categoryDescription == null || categoryDescription.trim().isEmpty()) {
            return buildViolation(context, "Category Description is required.");
        }

        if (categoryDescription.length() < 3 || categoryDescription.length() > 100) {
            return buildViolation(context, "Category Description must be between 3 and 100 characters.");
        }

        return true;
    }

    private boolean buildViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
               .addConstraintViolation();
        return false;
    }
}
