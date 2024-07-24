package com.example.RestaurantService.service;

import com.example.RestaurantService.model.Restaurant;
import com.example.RestaurantService.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
public class RestaurantService {

    @Autowired
    private RestaurantRepository repository;

    public ResponseEntity<?> getRestaurants() {
        return repository.getRestaurants();
    }

    public ResponseEntity<?> getRestaurant(UUID id) {
        return repository.getRestaurant(id);
    }

    public ResponseEntity<?> createRestaurant(Restaurant restaurant) {
        return repository.createRestaurant(restaurant);
    }

    public ResponseEntity<?> updateRestaurant(UUID id, Restaurant restaurant) {
        return repository.updateRestaurant(id, restaurant);
    }

    public ResponseEntity<?> deleteRestaurant(UUID id) {
        return repository.deleteRestaurant(id);
    }





}
