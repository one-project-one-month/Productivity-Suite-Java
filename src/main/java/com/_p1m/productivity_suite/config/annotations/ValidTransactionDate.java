package com._p1m.productivity_suite.config.annotations;

import com._p1m.productivity_suite.config.validators.TransactionDateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TransactionDateValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTransactionDate {
    String message() default "Invalid transaction date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
