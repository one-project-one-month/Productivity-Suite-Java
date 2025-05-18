package com._p1m.productivity_suite.config.validators;

import com._p1m.productivity_suite.config.annotations.ValidTransactionAmount;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TransactionAmountValidator implements ConstraintValidator<ValidTransactionAmount, String> {

    @Override
    public boolean isValid(final String transactionAmount, final ConstraintValidatorContext context) {
       if(transactionAmount == null || transactionAmount.trim().isEmpty()){
           return buildViolation(context,"Transaction Value is required.");
       }
       String trimmed = transactionAmount.trim();
       try{
           int amount = Integer.parseInt(trimmed);
           if(amount < 1 || amount > 999999){
               return buildViolation(context,"Transaction Amount must be between 1 and 999999.");
           }
       }catch(NumberFormatException e){
           return buildViolation(context,"Transaction Amount must be a valid number.");
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
