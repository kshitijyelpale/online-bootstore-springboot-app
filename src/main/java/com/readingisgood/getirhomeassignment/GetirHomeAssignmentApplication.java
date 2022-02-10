package com.readingisgood.getirhomeassignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class GetirHomeAssignmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(GetirHomeAssignmentApplication.class, args);
    }

}
