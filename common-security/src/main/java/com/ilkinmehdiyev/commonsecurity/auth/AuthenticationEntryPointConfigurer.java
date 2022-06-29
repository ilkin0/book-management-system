package com.ilkinmehdiyev.commonsecurity.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class AuthenticationEntryPointConfigurer implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String exceptionMessage = authException.getMessage();
        log.error("Responding with unauthorized error: {}", exceptionMessage);

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exceptionMessage);
    }
}
