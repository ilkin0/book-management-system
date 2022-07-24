package com.ilkinmehdiyev.usermanagement.validation.interfaces;

import com.ilkinmehdiyev.usermanagement.validation.validator.PasswordConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = PasswordConstraintValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
public @interface PasswordConstraint {

    String message() default "Invalid Password. Password does not meet requirements.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
