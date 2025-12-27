package com.kojstarinnovations.terminal.auths;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * App - This class is responsible for starting the application
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@SpringBootApplication(scanBasePackages = "com.kojstarinnovations.terminal.auths")
@EnableScheduling
public class AuthServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApp.class, args);
    }
}