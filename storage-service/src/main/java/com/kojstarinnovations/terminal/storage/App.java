package com.kojstarinnovations.terminal.storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * App - Main class for the storage service
 *
 * @Author: Kojstar Innovations (Augusto Vicente)
 */
@SpringBootApplication(scanBasePackages = "com.kojstarinnovations.terminal.storage")
public class App {

    /**
     * Main method for the storage service
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}