package com.ilkinmehdiyev.usermanagement.service.interfaces;

import com.ilkinmehdiyev.usermanagement.model.Otp;
import com.ilkinmehdiyev.usermanagement.model.User;
import com.ilkinmehdiyev.usermanagement.model.enums.OtpType;

public interface OtpService {
    Otp getByUserId(long userId);

    Otp getByOtp(String otp);

    Otp generateOtp(User user, OtpType type);

    String verifyOtp(String otpCode);
}
