package com.ilkinmehdiyev.usermanagement.service.impl;

import com.ilkinmehdiyev.common.exception.ApplicationException;
import com.ilkinmehdiyev.commonsecurity.auth.services.interfaces.JwtService;
import com.ilkinmehdiyev.usermanagement.dto.SignInRequestDto;
import com.ilkinmehdiyev.usermanagement.dto.jwt.AccessTokenResponseDto;
import com.ilkinmehdiyev.usermanagement.dto.jwt.JwtTokenResponseDto;
import com.ilkinmehdiyev.usermanagement.dto.jwt.RefreshTokenDto;
import com.ilkinmehdiyev.usermanagement.model.RefreshToken;
import com.ilkinmehdiyev.usermanagement.repo.RefreshTokenRepository;
import com.ilkinmehdiyev.usermanagement.service.interfaces.AuthService;
import com.ilkinmehdiyev.usermanagement.service.interfaces.RefreshTokenService;
import com.ilkinmehdiyev.usermanagement.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static com.ilkinmehdiyev.usermanagement.error.Error.AUTHENTICATION_FAILED;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final JwtService jwtService;

    private final RefreshTokenRepository refreshTokenRepository;

    private final RefreshTokenService refreshTokenService;

    private final UserService userService;

    private final Duration DURATION_ONE_DAY = Duration.ofDays(1);
    private final Duration DURATION_THREE_DAY = Duration.ofDays(3);

    @Override
    public JwtTokenResponseDto signIn(SignInRequestDto requestDto) {

        log.info("Authentication requested by {}", requestDto.username());

        var authenticationToken =
                new UsernamePasswordAuthenticationToken(requestDto.username(), requestDto.password());
        Authentication authenticate =
                authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        Duration duration = requestDto.remember() ? DURATION_THREE_DAY : DURATION_ONE_DAY;

        String issueToken = jwtService.issueToken(authenticate, duration);
        var accessToken = new AccessTokenResponseDto(issueToken);

        RefreshTokenDto refreshToken = issueRefreshToken(authenticate);

        return new JwtTokenResponseDto(accessToken, refreshToken);
    }

    private RefreshTokenDto issueRefreshToken(Authentication authenticate) {
        RefreshToken refreshToken =
                RefreshToken.builder()
                        .username(authenticate.getName())
                        .token(UUID.randomUUID().toString())
                        .expiryDate(LocalDateTime.now().plus(10, ChronoUnit.MINUTES))
                        .valid(true)
                        .build();

        refreshTokenRepository.save(refreshToken);
        return new RefreshTokenDto(refreshToken.getToken(), refreshToken.getExpiryDate());
    }

    @Override
    public JwtTokenResponseDto refreshAccessToken(RefreshTokenDto tokenDto) {
        RefreshToken refreshToken = refreshTokenService.getByToken(tokenDto.token());

        if (refreshToken.isValid() && refreshToken.getExpiryDate().isAfter(LocalDateTime.now())) {
            UserDetails user = userService.loadUserByUsername(refreshToken.getUsername());
            if (isAccountActive(user)) {
                refreshToken.setValid(false);
                refreshTokenRepository.save(refreshToken);

                var authToken = buildAuthToken(user);
                return new JwtTokenResponseDto(issueAccessToken(authToken), issueRefreshToken(authToken));
            }
        }
        throw new ApplicationException(AUTHENTICATION_FAILED);
    }

    private AccessTokenResponseDto issueAccessToken(Authentication authToken) {
        return new AccessTokenResponseDto(jwtService.issueToken(authToken, DURATION_ONE_DAY));
    }

    private UsernamePasswordAuthenticationToken buildAuthToken(UserDetails user) {
        return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
    }

    private boolean isAccountActive(UserDetails user) {
        return user.isAccountNonExpired()
                && user.isAccountNonLocked()
                && user.isCredentialsNonExpired()
                && user.isEnabled();
    }
}
