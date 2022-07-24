package com.ilkinmehdiyev.usermanagement.repo;

import com.ilkinmehdiyev.usermanagement.model.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, Long> {
    Optional<Otp> findByOtp(String otp);

    Optional<Otp> findByUserId(long userId);
}
