package com.ilkinmehdiyev.commonsecurity.auth.services.interfaces;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface AuthService {

    Optional<Authentication> getAuthentication(HttpServletRequest request);
}
