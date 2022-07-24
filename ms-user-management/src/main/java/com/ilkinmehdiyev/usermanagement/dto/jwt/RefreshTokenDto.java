package com.ilkinmehdiyev.usermanagement.dto.jwt;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public record RefreshTokenDto(@NotBlank String token, LocalDateTime expiryDate) {
}
