package com.ilkinmehdiyev.usermanagement.dto;

import com.ilkinmehdiyev.usermanagement.validation.interfaces.ConfirmPasswordConstraint;
import com.ilkinmehdiyev.usermanagement.validation.interfaces.PasswordConstraint;

import javax.validation.constraints.NotBlank;

@ConfirmPasswordConstraint
public record UserRegisterRequestDto(
        @NotBlank String username, @NotBlank String name, @NotBlank String email, @NotBlank String phoneNumber,
        @NotBlank @PasswordConstraint String password, @NotBlank String confirmPassword
) {
}