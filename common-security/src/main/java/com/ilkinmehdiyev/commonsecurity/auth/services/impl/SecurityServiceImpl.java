package com.ilkinmehdiyev.commonsecurity.auth.services.impl;

import com.ilkinmehdiyev.commonsecurity.auth.services.interfaces.SecurityService;
import com.ilkinmehdiyev.commonsecurity.enums.UserRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Override
    public Optional<String> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
                .map(authentication -> {
                    if (authentication.getPrincipal() instanceof UserDetails springSecurtiyUser)
                        return springSecurtiyUser.getUsername();
                    else if (authentication.getPrincipal() instanceof String s) {
                        return s;
                    }
                    return null;
                });
    }

    @Override
    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication != null && getAuthorities(authentication)
                .noneMatch(UserRole.ROLE_ANONYMOUS.name()::equals);
    }

    private Stream<String> getAuthorities(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority);
    }

}

