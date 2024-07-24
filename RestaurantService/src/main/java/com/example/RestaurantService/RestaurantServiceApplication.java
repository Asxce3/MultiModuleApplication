package com.example.RestaurantService;

import com.example.RestaurantService.controllers.RestaurantController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestaurantServiceApplication  {

    public static void main(String[] args) {
        SpringApplication.run(RestaurantServiceApplication.class, args);
        System.out.println("RestaurantService started");
    }


}
