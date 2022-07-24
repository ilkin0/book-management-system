package com.ilkinmehdiyev.usermanagement.security;

import com.ilkinmehdiyev.commonsecurity.auth.services.interfaces.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SpringSecurityAuditorAware implements AuditorAware<String> {
    private final SecurityService securityService;

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(securityService.getCurrentUserLogin().orElse("system-user"));
    }
}
