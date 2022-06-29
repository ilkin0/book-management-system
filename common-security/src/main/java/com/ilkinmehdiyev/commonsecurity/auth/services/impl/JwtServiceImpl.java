package com.ilkinmehdiyev.commonsecurity.auth.services.impl;

import com.ilkinmehdiyev.commonsecurity.auth.services.interfaces.ClaimProvider;
import com.ilkinmehdiyev.commonsecurity.auth.services.interfaces.ClaimSetProvider;
import com.ilkinmehdiyev.commonsecurity.auth.services.interfaces.JwtService;
import com.ilkinmehdiyev.commonsecurity.config.SecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final SecurityProperties applicationProperties;

    private final Set<ClaimSetProvider> claimSetProviders;

    private final Set<ClaimProvider> claimProviders;
    private Key key;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(applicationProperties.getJwtProperties().getSecret());
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public String issueToken(Authentication authentication, Duration duration) {
        log.trace("Issue JWT token to {} for {}", authentication.getPrincipal(), duration);

        final var jwtBuilder = Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plus(duration)))
                .setHeader(Map.of("type", "JWT"))
                .signWith(key, SignatureAlgorithm.RS512);

        addClaimsSets(jwtBuilder, authentication);
        addClaims(jwtBuilder, authentication);

        return jwtBuilder.compact();
    }

    @Override
    public void addClaimsSets(JwtBuilder jwtBuilder, Authentication authentication) {
        claimSetProviders.forEach(setProvider -> {
            final var claimSet = setProvider.provide(authentication);
            log.trace("Adding claim {}", claimSet);

            jwtBuilder.claim(claimSet.key(), claimSet.claims());
        });
    }

    @Override
    public void addClaims(JwtBuilder jwtBuilder, Authentication authentication) {
        claimProviders.forEach(provider -> {
            final var claim = provider.provide(authentication);
            log.trace("Adding claim: {}", claim);

            jwtBuilder.claim(claim.key(), claim.claim());
        });
    }
}
