package com.ilkinmehdiyev.usermanagement.service.interfaces;

import com.ilkinmehdiyev.usermanagement.model.RefreshToken;

public interface RefreshTokenService {
    RefreshToken getByToken(String token);
}
