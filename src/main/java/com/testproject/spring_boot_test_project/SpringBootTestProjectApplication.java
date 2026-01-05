package com.testproject.spring_boot_test_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Spring Boot application.
 * The main method launches the application using SpringApplication.run().
 */
@SpringBootApplication
public class SpringBootTestProjectApplication {

    /**
     * Application entry point.
     * SpringApplication.run() bootstraps the Spring context, starts the embedded server,
     * and makes the API available at http://localhost:8080
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringBootTestProjectApplication.class, args);
    }

}
