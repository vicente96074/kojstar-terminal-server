package com.kojstarinnovations.terminal.us;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main class to start the application
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@SpringBootApplication(scanBasePackages = "com.kojstarinnovations.terminal.us")
@EnableScheduling
@EnableFeignClients
public class UserServiceApp {

    /**
     * Main method to start the application
     *
     * @param args - arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApp.class, args);
    }
}