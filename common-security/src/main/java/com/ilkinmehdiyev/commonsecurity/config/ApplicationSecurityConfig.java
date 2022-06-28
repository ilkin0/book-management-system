package com.ilkinmehdiyev.commonsecurity.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public interface ApplicationSecurityConfig {
    void configure(HttpSecurity http);
}
