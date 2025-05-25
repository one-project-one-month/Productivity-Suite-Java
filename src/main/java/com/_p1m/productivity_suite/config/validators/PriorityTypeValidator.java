package com._p1m.productivity_suite.config.validators;

import com._p1m.productivity_suite.config.annotations.ValidPriorityType;
import com._p1m.productivity_suite.data.enums.PriorityType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PriorityTypeValidator implements ConstraintValidator<ValidPriorityType, Integer> {
    @Override
    public boolean isValid(final Integer value, final ConstraintValidatorContext context) {
        return PriorityType.isValidValue(value);
    }
}
