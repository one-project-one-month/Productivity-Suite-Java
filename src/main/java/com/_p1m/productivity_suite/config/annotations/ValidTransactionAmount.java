package com._p1m.productivity_suite.config.annotations;

import com._p1m.productivity_suite.config.validators.TransactionAmountValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TransactionAmountValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTransactionAmount {
    String message() default "Invalid Transaction Amount";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
