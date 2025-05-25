package com._p1m.productivity_suite.config.annotations;

import com._p1m.productivity_suite.config.validators.TransactionDescriptionValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TransactionDescriptionValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTransactionDescription {
    String message() default "Invalid Transaction Description";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
