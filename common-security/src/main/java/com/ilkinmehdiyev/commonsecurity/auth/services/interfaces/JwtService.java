package com.ilkinmehdiyev.commonsecurity.auth.services.interfaces;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import org.springframework.security.core.Authentication;

import java.time.Duration;

public interface JwtService {
    Claims parseToken(String token);

    String issueToken(Authentication authentication, Duration duration);

    void addClaimsSets(JwtBuilder jwtBuilder, Authentication authentication);

    void addClaims(JwtBuilder jwtBuilder, Authentication authentication);
}

