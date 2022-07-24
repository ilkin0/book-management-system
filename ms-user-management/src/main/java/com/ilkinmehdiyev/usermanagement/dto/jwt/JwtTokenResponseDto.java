package com.ilkinmehdiyev.usermanagement.dto.jwt;

public record JwtTokenResponseDto(AccessTokenResponseDto accessToken, RefreshTokenDto refreshTokenDto) {
}