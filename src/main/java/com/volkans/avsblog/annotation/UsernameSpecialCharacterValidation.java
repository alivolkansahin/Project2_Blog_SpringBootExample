package com.volkans.avsblog.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = UsernameValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameSpecialCharacterValidation {
    String message() default "Username must not contain special characters!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
