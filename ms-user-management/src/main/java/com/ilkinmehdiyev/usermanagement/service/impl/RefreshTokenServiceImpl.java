package com.ilkinmehdiyev.usermanagement.service.impl;

import com.ilkinmehdiyev.common.exception.ApplicationException;
import com.ilkinmehdiyev.usermanagement.model.RefreshToken;
import com.ilkinmehdiyev.usermanagement.repo.RefreshTokenRepository;
import com.ilkinmehdiyev.usermanagement.service.interfaces.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.ilkinmehdiyev.usermanagement.error.Error.TOKEN_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository tokenRepository;

    @Override
    public RefreshToken getByToken(String token) {
        return tokenRepository
                .findByToken(token)
                .orElseThrow(
                        () -> {
                            log.error("Cannot find RefreshToken with {} token", token);
                            return new ApplicationException(TOKEN_NOT_FOUND);
                        });
    }
}
