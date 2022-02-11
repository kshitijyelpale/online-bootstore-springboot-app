package com.readingisgood.getirhomeassignment;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@OpenAPIDefinition(info = @Info(title = "Book Store API", version = "1.0"))
public class GetirHomeAssignmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(GetirHomeAssignmentApplication.class, args);
    }

}
