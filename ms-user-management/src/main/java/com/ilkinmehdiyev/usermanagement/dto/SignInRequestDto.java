package com.ilkinmehdiyev.usermanagement.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record SignInRequestDto(@NotBlank String username, @NotBlank String password, @NotNull boolean remember) {
}