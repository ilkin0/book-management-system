package com.ilkinmehdiyev.commonsecurity.auth.services.interfaces;

import com.ilkinmehdiyev.commonsecurity.domain.Claim;
import org.springframework.security.core.Authentication;

public interface ClaimProvider {
    Claim provide(Authentication authentication);
}
