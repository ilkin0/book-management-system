package com.ilkinmehdiyev.usermanagement.config;

import com.ilkinmehdiyev.commonsecurity.config.ApplicationSecurityConfig;
import lombok.SneakyThrows;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
@ComponentScan("com.ilkinmehdiyev.commonsecurity")
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration implements ApplicationSecurityConfig {

    @Override
    @SneakyThrows
    public void configure(HttpSecurity http) {
        http.authorizeRequests()
                .antMatchers(
                        "/api/v1/auth/sign-up",
                        "/api/v1/auth/sign-in",
                        "/api/v1/auth/refresh-token",
                        "/api/v1/auth/password-reset",
                        "/api/v1/auth/sca/otp/**")
                .permitAll();
    }
}
