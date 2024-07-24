package org.example.authservice;

import org.example.authservice.config.AuthConfig;
import org.example.authservice.models.Candidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthServiceApplication{

    @Autowired
    AuthConfig authConfig;



    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
        System.out.println("AuthService Started");
    }


}
