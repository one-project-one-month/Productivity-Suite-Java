package com._p1m.productivity_suite.config.validators;

import com._p1m.productivity_suite.config.annotations.ValidStatusType;
import com._p1m.productivity_suite.data.enums.StatusType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StatusTypeValidator implements ConstraintValidator<ValidStatusType, Integer> {
    @Override
    public boolean isValid(final Integer value, final ConstraintValidatorContext context) {
        return StatusType.isValidValue(value);
    }
}
