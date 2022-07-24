package com.ilkinmehdiyev.usermanagement.service.impl;

import com.ilkinmehdiyev.common.exception.ApplicationException;
import com.ilkinmehdiyev.usermanagement.config.properties.OtpProperties;
import com.ilkinmehdiyev.usermanagement.model.Otp;
import com.ilkinmehdiyev.usermanagement.model.User;
import com.ilkinmehdiyev.usermanagement.model.enums.OtpStatus;
import com.ilkinmehdiyev.usermanagement.model.enums.OtpType;
import com.ilkinmehdiyev.usermanagement.repo.OtpRepository;
import com.ilkinmehdiyev.usermanagement.service.interfaces.OtpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.UUID;

import static com.ilkinmehdiyev.usermanagement.error.Error.OTP_EXPIRED;
import static com.ilkinmehdiyev.usermanagement.error.Error.OTP_IS_ALREADY_USED;
import static com.ilkinmehdiyev.usermanagement.error.Error.OTP_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpServiceImpl implements OtpService {
    private final OtpRepository otpRepository;

    private final OtpProperties properties;

    @Override
    public Otp getByUserId(long userId) {
        return otpRepository
                .findByUserId(userId)
                .orElseThrow(() -> new ApplicationException(OTP_NOT_FOUND));
    }

    @Override
    public Otp getByOtp(String otp) {
        return otpRepository.findByOtp(otp).orElseThrow(() -> new ApplicationException(OTP_NOT_FOUND));
    }

    @Override
    public Otp generateOtp(User user, OtpType type) {
        Otp otp =
                Otp.builder()
                        .otp(UUID.randomUUID().toString())
                        .type(type)
                        .status(OtpStatus.NEW)
                        .otpLifeTime(properties.getOtpLifeTime())
                        .creationTime(new Timestamp(System.currentTimeMillis()))
                        .user(user)
                        .build();

        return otpRepository.save(otp);
    }

    @Override
    @Transactional
    public String verifyOtp(String otpCode) {
        Otp otp = getByOtp(otpCode);
        validateOtp(otp);

        otp.setStatus(OtpStatus.VERIFIED);

        activateUser(otp);

        try {
            otpRepository.save(otp);
            log.info("OTP {} has been saved", otp);
        } catch (Exception e) {
            log.error("Error occurred during OTP save");
        }

        return otp.getOtp();
    }

    private void validateOtp(Otp otp) {
        int otpLifeTime = otp.getOtpLifeTime();
        long currentTimestamp = System.currentTimeMillis();
        long otpCreatedTime = otp.getCreationTime().getTime();

        if (otpCreatedTime + otpLifeTime < currentTimestamp)
            throw new ApplicationException(OTP_EXPIRED);

        if (otp.getStatus() != OtpStatus.NEW) throw new ApplicationException(OTP_IS_ALREADY_USED);
    }

    private void activateUser(Otp otp) {
        User user = otp.getUser();

        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);

        log.info("User {} is activated", user.getUsername());
    }
}
