package com.ilkinmehdiyev.usermanagement.service.interfaces;

import com.ilkinmehdiyev.usermanagement.dto.UserRegisterRequestDto;
import com.ilkinmehdiyev.usermanagement.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User createUser(UserRegisterRequestDto registerDto);

    String signUp(UserRegisterRequestDto requestDto);

    User getById(long id);
}
