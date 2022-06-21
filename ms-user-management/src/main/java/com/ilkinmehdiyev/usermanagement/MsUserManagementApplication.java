package com.ilkinmehdiyev.usermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
//        (exclude={DataSourceAutoConfiguration.class})
public class MsUserManagementApplication {
    public static void main(String... args) {
        SpringApplication.run(MsUserManagementApplication.class);
    }
}
