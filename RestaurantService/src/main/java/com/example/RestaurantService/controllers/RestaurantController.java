package com.example.RestaurantService.controllers;

import com.example.RestaurantService.model.Restaurant;
import com.example.RestaurantService.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;


    @GetMapping()
    public ResponseEntity<?> getMany(){
        return restaurantService.getRestaurants();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable UUID id){
        return restaurantService.getRestaurant(id);
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody() Restaurant restaurant){
        return restaurantService.createRestaurant(restaurant);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable() UUID id, @RequestBody Restaurant restaurant) {
        return restaurantService.updateRestaurant(id, restaurant);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable() UUID id){
        return restaurantService.deleteRestaurant(id);
    }

}
