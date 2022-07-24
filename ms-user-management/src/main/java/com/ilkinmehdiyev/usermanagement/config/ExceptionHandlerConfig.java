package com.ilkinmehdiyev.usermanagement.config;

import com.ilkinmehdiyev.common.exception.GlobalExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ExceptionHandlerConfig extends GlobalExceptionHandler {
    public ExceptionHandlerConfig(MessageSource messageSource) {
        super(messageSource);
    }
}
