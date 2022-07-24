package com.ilkinmehdiyev.usermanagement.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({SecurityConfiguration.class, ExceptionHandlerConfig.class})
public class ApplicationConfiguration {
}
