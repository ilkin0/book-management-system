package com.ilkinmehdiyev.usermanagement.validation.validator;

import com.ilkinmehdiyev.usermanagement.validation.interfaces.PasswordConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.StringJoiner;
import java.util.regex.Pattern;

public class PasswordConstraintValidator
        implements ConstraintValidator<PasswordConstraint, String> {

    public static final String VALID_PASSWORD_REGEX =
            "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–{}:;',?/*~$^+=<>]).{8,20}$";
    private String errorMessage;

    @Override
    public void initialize(PasswordConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.errorMessage = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        final Pattern pattern = Pattern.compile(VALID_PASSWORD_REGEX);
        boolean matches = pattern.matcher(value).matches();

        if (matches) return true;

        context.disableDefaultConstraintViolation();
        context
                .buildConstraintViolationWithTemplate(
                        String.valueOf(new StringJoiner(",").add(errorMessage)))
                .addConstraintViolation();

        return false;
    }
}
