package com.ilkinmehdiyev.commonsecurity.corsConfiguration;

import com.ilkinmehdiyev.commonsecurity.config.SecurityProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import static java.util.Objects.nonNull;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SwaggerCorsConfig {
    private final SecurityProperties securityProperties;

    @Bean
    public CorsFilter corsFilter() {
        var source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = securityProperties.getCorsConfiguration();

        if (nonNull(corsConfiguration.getAllowedOrigins()) && !corsConfiguration.getAllowedOrigins().isEmpty()) {
            log.debug("Registering CORS filter");

            source.registerCorsConfiguration("/api/**", corsConfiguration);
            source.registerCorsConfiguration("/management/**", corsConfiguration);
            source.registerCorsConfiguration("/v2/api-docs", corsConfiguration);
            source.registerCorsConfiguration("/v3/api-docs", corsConfiguration);
            source.registerCorsConfiguration("/*/api/**", corsConfiguration);
//            source.registerCorsConfiguration("/services/*/api/**", corsConfiguration);
//            source.registerCorsConfiguration("/*/management/**", corsConfiguration);
        }
        return new CorsFilter(source);
    }
}
