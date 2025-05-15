package com._p1m.productivity_suite.config.validators;

import com._p1m.productivity_suite.config.annotations.ValidCategoryType;
import com._p1m.productivity_suite.data.enums.CategoryType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CategoryTypeValidator implements ConstraintValidator<ValidCategoryType, Integer> {

    public boolean isValid(final Integer value, final ConstraintValidatorContext context) {
        return CategoryType.isValidValue(value);
    }
}
