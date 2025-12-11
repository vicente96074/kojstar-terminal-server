package com.kojstarinnovations.terminal.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * App - Main application class for the OAuth2 service.
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@SpringBootApplication(scanBasePackages = "com.kojstarinnovations.terminal.oauth2")
@EnableDiscoveryClient
@EnableFeignClients
public class App {

    /**
     * Main method to run the OAuth2 application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}