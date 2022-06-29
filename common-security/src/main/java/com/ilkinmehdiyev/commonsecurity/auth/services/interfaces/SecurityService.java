package com.ilkinmehdiyev.commonsecurity.auth.services.interfaces;

import java.util.Optional;

public interface SecurityService {
    Optional<String> getCurrentUserLogin();

    boolean isAuthenticated();
}
