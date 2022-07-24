package com.ilkinmehdiyev.usermanagement.validation.interfaces;

import com.ilkinmehdiyev.usermanagement.validation.validator.ConfirmPasswordConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = ConfirmPasswordConstraintValidator.class)
@Target({TYPE})
@Retention(RUNTIME)
public @interface ConfirmPasswordConstraint {
    String message() default "Confirm Password does not match with Password.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
