package com.volkans.avsblog.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordDigitValidation {
    String message() default "Password must contain at least one digit";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
