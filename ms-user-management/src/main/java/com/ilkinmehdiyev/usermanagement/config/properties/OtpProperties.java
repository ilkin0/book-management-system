package com.ilkinmehdiyev.usermanagement.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "otp")
public class OtpProperties {
    private int otpLifeTime;
    private int interval;
    private int maxAllowedAttemptsTimedBlocking;
    private int maxAllowedAttemptsPermanentBlocking;
}
