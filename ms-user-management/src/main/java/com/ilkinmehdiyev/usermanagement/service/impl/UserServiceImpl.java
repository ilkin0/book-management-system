package com.ilkinmehdiyev.usermanagement.service.impl;

import com.ilkinmehdiyev.common.exception.ApplicationException;
import com.ilkinmehdiyev.usermanagement.dto.UserRegisterRequestDto;
import com.ilkinmehdiyev.usermanagement.model.Otp;
import com.ilkinmehdiyev.usermanagement.model.User;
import com.ilkinmehdiyev.usermanagement.model.enums.OtpType;
import com.ilkinmehdiyev.usermanagement.repo.UserRepository;
import com.ilkinmehdiyev.usermanagement.service.interfaces.OtpService;
import com.ilkinmehdiyev.usermanagement.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

import static com.ilkinmehdiyev.usermanagement.error.Error.USERNAME_NOT_FOUND;
import static com.ilkinmehdiyev.usermanagement.error.Error.USER_ALREADY_EXISTS;
import static com.ilkinmehdiyev.usermanagement.error.Error.USER_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final OtpService otpService;

    @Override
    public User createUser(UserRegisterRequestDto registerDto) {

        User user =
                User.builder()
                        .username(registerDto.username())
                        .name(registerDto.name())
                        .email(registerDto.email())
                        .password(passwordEncoder.encode(registerDto.password()))
                        .phoneNumber(registerDto.phoneNumber())
                        .build();

        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.trace("Authenticating user: {}", username);
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ApplicationException(USERNAME_NOT_FOUND));
    }

    @Override
    @Transactional
    public String signUp(UserRegisterRequestDto requestDto) {

        checkIfUserExist(requestDto.username());

        User user = createUser(requestDto);
        log.info("User have been created: {}", user);

        Otp otp = otpService.generateOtp(user, OtpType.SIGN_UP);
        log.info("Otp has been generated for the user {}. Otp: {}", user, otp);

        // TODO MS-Notification send OTP confirm notification via SMS and Email ASYNC !!!
        return otp.getOtp();
    }

    private void checkIfUserExist(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent())
            throw new ApplicationException(USER_ALREADY_EXISTS, Map.of("username", username));
    }

    @Override
    public User getById(long id) {
        return userRepository
                .findById(id)
                .orElseThrow(
                        () -> {
                            log.error("Cannot find USer with {} id", id);
                            return new ApplicationException(USER_NOT_FOUND);
                        });
    }
}
