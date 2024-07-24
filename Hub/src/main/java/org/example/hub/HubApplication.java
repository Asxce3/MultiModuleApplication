package org.example.hub;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class HubApplication {
    public static void main(String[] args) {
        SpringApplication.run(HubApplication.class, args);
        System.out.println("Hub Application Started");
    }

}
