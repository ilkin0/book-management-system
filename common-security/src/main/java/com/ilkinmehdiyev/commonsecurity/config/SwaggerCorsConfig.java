package com.ilkinmehdiyev.commonsecurity.corsConfiguration;

import com.ilkinmehdiyev.commonsecurity.config.SecurityProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SwaggerCorsConfig {
    private final SecurityProperties securityProperties;

    // Migrate from SpringFox to OpenAPI(SpringDoc)  TODO!!!

    //
    //    @Bean
    //    public CorsFilter corsFilter() {
    //        var source = new UrlBasedCorsConfigurationSource();
    //
    //        CorsConfiguration corsConfiguration = securityProperties.getCorsConfiguration();
    //
    //        if (nonNull(corsConfiguration.getAllowedOrigins()) &&
    // !corsConfiguration.getAllowedOrigins().isEmpty()) {
    //            log.debug("Registering CORS filter");
    //
    //            source.registerCorsConfiguration("/api/**", corsConfiguration);
    //            source.registerCorsConfiguration("/management/**", corsConfiguration);
    //            source.registerCorsConfiguration("/v2/api-docs", corsConfiguration);
    //            source.registerCorsConfiguration("/v3/api-docs", corsConfiguration);
    //            source.registerCorsConfiguration("/*/api/**", corsConfiguration);
    ////            source.registerCorsConfiguration("/services/*/api/**", corsConfiguration);
    ////            source.registerCorsConfiguration("/*/management/**", corsConfiguration);
    //        }
    //        return new CorsFilter(source);
    //    }
}
