package com.ilkinmehdiyev.commonsecurity.config;

import com.ilkinmehdiyev.commonsecurity.auth.JwtAuthFilterConfigurerAdapter;
import com.ilkinmehdiyev.commonsecurity.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.StringJoiner;

import static com.ilkinmehdiyev.commonsecurity.enums.UserRole.ROLE_ADMIN;
import static com.ilkinmehdiyev.commonsecurity.enums.UserRole.ROLE_USER;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class BaseSecurityConfig {

    private final ApplicationSecurityConfig securityConfig;
    private final SecurityProperties securityProperties;

    private final JwtAuthFilterConfigurerAdapter authFilterConfigurerAdapter;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        securityConfig.configure(http);

        http.csrf().disable()
                .cors().configurationSource(getCorsConfigurationSource());

        // No session will be created or used by spring security
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));

        //Allow swagger and actuator
        http.authorizeRequests().antMatchers(getPermitUrls()).authenticated();

        //Disallow all requests by default unless explicitly defined in submodules
        http.authorizeRequests().anyRequest().access(getAuthorities(ROLE_USER.name(), ROLE_ADMIN.name()));

        http.apply(authFilterConfigurerAdapter);

        return http.build();
    }

    protected String getAuthorities(Object... roles) {
        var joiner = new StringJoiner(" or ");

        for (Object role : roles) {
            if (role instanceof UserRole userRole)
                joiner.add(getAuthority(userRole));
            else
                joiner.add(getAuthority(role.toString()));
        }

        return joiner.toString();
    }

    private String getAuthority(String userRole) {
        return "hasAuthority('" + userRole + "')";
    }

    protected String getAuthority(UserRole userRole) {
        return "hasAuthority('" + userRole.name() + "')";
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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
