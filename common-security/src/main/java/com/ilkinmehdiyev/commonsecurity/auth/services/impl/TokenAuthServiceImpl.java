package com.ilkinmehdiyev.commonsecurity.auth.services.impl;

import com.ilkinmehdiyev.commonsecurity.auth.services.interfaces.AuthService;
import com.ilkinmehdiyev.commonsecurity.auth.services.interfaces.JwtService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenAuthServiceImpl implements AuthService {

    public final String AUTHORITIES_CLAIM = "authorities";
    public final String BEARER_AUTH_HEADER = "Bearer";


    private final JwtService jwtService;

    @Override
    public Optional<Authentication> getAuthentication(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(AUTHORIZATION))
                .filter(this::isBearerAuth)
                .flatMap(this::getAuthenticationBearer);
    }

    private Optional<Authentication> getAuthenticationBearer(String header) {
        String token = header.substring(BEARER_AUTH_HEADER.length()).trim();
        Claims claims = jwtService.parseToken(token);
        log.trace("The claims parsed: {}", claims);

        if (claims.getExpiration().before(new Date()))
            return Optional.empty();

        return Optional.of(getAuthenticationBearer(claims));
    }

    private Authentication getAuthenticationBearer(Claims claims) {
        List<?> roles = claims.get(AUTHORITIES_CLAIM, List.class);

        List<SimpleGrantedAuthority> authorityList = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.toString())).toList();

        return new UsernamePasswordAuthenticationToken(claims.getSubject(), "", authorityList);
    }

    private boolean isBearerAuth(String header) {
        return header.toLowerCase().startsWith(BEARER_AUTH_HEADER.toLowerCase());
    }
}
