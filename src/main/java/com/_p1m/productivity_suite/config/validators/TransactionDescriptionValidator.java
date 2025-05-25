package com._p1m.productivity_suite.config.validators;

import com._p1m.productivity_suite.config.annotations.ValidTransactionDescription;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TransactionDescriptionValidator implements ConstraintValidator<ValidTransactionDescription, String> {

    @Override
    public boolean isValid(final String transactionDescription, final ConstraintValidatorContext context) {
        if(transactionDescription == null || transactionDescription.trim().isEmpty()){
            return buildViolation(context,"Transaction Description is required.");
        }
        if(transactionDescription.length() <3 || transactionDescription.length() > 100){
            return buildViolation(context,"Category Description must be between 3 and 100 characters.");
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
