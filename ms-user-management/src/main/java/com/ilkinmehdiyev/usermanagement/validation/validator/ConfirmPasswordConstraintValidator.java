package com.ilkinmehdiyev.usermanagement.validation.validator;

import com.ilkinmehdiyev.usermanagement.dto.UserRegisterRequestDto;
import com.ilkinmehdiyev.usermanagement.validation.interfaces.ConfirmPasswordConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.StringJoiner;

public class ConfirmPasswordConstraintValidator
        implements ConstraintValidator<ConfirmPasswordConstraint, UserRegisterRequestDto> {

    public String errorMessage;

    @Override
    public void initialize(ConfirmPasswordConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.errorMessage = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(UserRegisterRequestDto requestDto, ConstraintValidatorContext context) {
        boolean equals = requestDto.password().equals(requestDto.confirmPassword());

        if (equals) return true;

        context.disableDefaultConstraintViolation();
        context
                .buildConstraintViolationWithTemplate(
                        String.valueOf(new StringJoiner(",").add(errorMessage)))
                .addConstraintViolation();

        return false;
    }
}
