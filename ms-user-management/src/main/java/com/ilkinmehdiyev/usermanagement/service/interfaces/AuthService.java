package com.ilkinmehdiyev.usermanagement.service.interfaces;

import com.ilkinmehdiyev.usermanagement.dto.SignInRequestDto;
import com.ilkinmehdiyev.usermanagement.dto.jwt.JwtTokenResponseDto;
import com.ilkinmehdiyev.usermanagement.dto.jwt.RefreshTokenDto;

public interface AuthService {
    JwtTokenResponseDto signIn(SignInRequestDto requestDto);

    JwtTokenResponseDto refreshAccessToken(RefreshTokenDto tokenDto);
}
