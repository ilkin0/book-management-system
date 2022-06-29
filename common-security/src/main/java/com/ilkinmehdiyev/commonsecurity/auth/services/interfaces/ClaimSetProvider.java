package com.ilkinmehdiyev.commonsecurity.auth.services.interfaces;

import com.ilkinmehdiyev.commonsecurity.domain.ClaimSet;
import org.springframework.security.core.Authentication;

public interface ClaimSetProvider {
    ClaimSet provide(Authentication authentication);
}
