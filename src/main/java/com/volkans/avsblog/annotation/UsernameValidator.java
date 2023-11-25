package com.volkans.avsblog.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class UsernameValidator implements ConstraintValidator<UsernameSpecialCharacterValidation, String> {
    @Override
    public void initialize(UsernameSpecialCharacterValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return username.matches("[a-zA-Z0-9]+");
    }
}
