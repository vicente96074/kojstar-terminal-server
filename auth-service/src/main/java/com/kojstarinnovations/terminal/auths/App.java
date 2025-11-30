package com.kojstarinnovations.terminal.auths;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * App - This class is responsible for starting the application
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@SpringBootApplication(scanBasePackages = "com.kojstarinnovations.terminal.auths")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}