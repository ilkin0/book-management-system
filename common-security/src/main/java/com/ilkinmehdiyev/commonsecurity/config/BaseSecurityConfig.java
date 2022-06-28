package com.ilkinmehdiyev.commonsecurity.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class BaseSecurityConfig {

    private final HttpSecurity http;
    private final ApplicationSecurityConfig securityConfig;
    private final SecurityProperties securityProperties;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        securityConfig.configure(http);

        http.csrf().disable()
                .cors().configurationSource(getCorsConfigurationSource());

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));

        http.authorizeRequests().antMatchers(getPermitUrls()).authenticated();

//        http.authorizeRequests().anyRequest().access() TODO

    }

    private CorsConfigurationSource getCorsConfigurationSource() {
        CorsConfiguration corsConfiguration = securityProperties.getCorsConfiguration();
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    private String[] getPermitUrls() {
        return new String[]{"/actuator/**", "/v2/api-docs", "/v3/api-docs",
                "/swagger-ui/**", "/swagger-ui.html"};
    }
}
