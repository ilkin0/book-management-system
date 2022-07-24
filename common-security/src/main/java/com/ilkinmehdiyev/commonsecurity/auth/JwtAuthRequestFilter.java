package com.ilkinmehdiyev.commonsecurity.auth;

import com.ilkinmehdiyev.commonsecurity.auth.services.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthRequestFilter extends OncePerRequestFilter {

    private final List<AuthService> authServices;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.trace("Filtering request against auth services {}", authServices.toString());

        var authOptional = Optional.empty();

        for (AuthService service : authServices) {
            authOptional = authOptional.or(() -> service.getAuthentication(request));
        }

        authOptional.ifPresent(
                auth -> SecurityContextHolder.getContext().setAuthentication((Authentication) auth));

        filterChain.doFilter(request, response);
    }
}
