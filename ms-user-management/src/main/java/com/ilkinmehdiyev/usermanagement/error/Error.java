package com.ilkinmehdiyev.usermanagement.error;

import com.ilkinmehdiyev.common.exception.ErrorResponse;
import org.springframework.http.HttpStatus;

public enum Error implements ErrorResponse {
    AUTHENTICATION_FAILED("AUTHENTICATION_FAILED", HttpStatus.UNAUTHORIZED, "Authentication failed"),
    USER_NOT_FOUND("USER_NOT_FOUND", HttpStatus.NOT_FOUND, "User not found with given id"),
    SIGN_UP_REQUEST_NOT_FOUND(
            "SIGN_UP_NOT_FOUND", HttpStatus.NOT_FOUND, "Sign up request not found with given id"),
    OTP_NOT_FOUND("OTP_NOT_FOUND", HttpStatus.NOT_FOUND, "Otp not found with given id"),
    OTP_EXPIRED("OTP_EXPIRED", HttpStatus.BAD_REQUEST, "Otp has already expired"),
    WRONG_OTP("WRONG_OTP", HttpStatus.BAD_REQUEST, "Otp is wrong"),
    OTP_IS_ALREADY_USED("OTP_IS_ALREADY_USED", HttpStatus.BAD_REQUEST, "Otp is already used"),
    USERNAME_NOT_FOUND("USERNAME_NOT_FOUND", HttpStatus.BAD_REQUEST, "User not found"),
    USER_ALREADY_EXISTS(
            "USER_ALREADY_EXISTS",
            HttpStatus.BAD_REQUEST,
            "The account with the given username {username} already exists."
                    + " Please try to reset your pin."),
    MAX_ATTEMPT_EXCEEDED(
            "MAX_ATTEMPT_EXCEEDED", HttpStatus.BAD_REQUEST, "Your number has been blocked for an hour."),
    MANY_REG_ATTEMPTS_ERROR(
            "MANY_REG_ATTEMPTS_ERROR",
            HttpStatus.BAD_REQUEST,
            "You exceeded max allowed attempts " + "therefore your number blocked permanently."),
    TOKEN_NOT_FOUND("TOKEN_NOT_FOUND", HttpStatus.UNAUTHORIZED, "Token not found!"),
    USER_NOT_ACTIVE("USER_NOT_ACTIVE", HttpStatus.UNAUTHORIZED, "User is not active!");

    String key;
    HttpStatus httpStatus;
    String message;

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    Error(String key, HttpStatus httpStatus, String message) {
        this.key = key;
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
