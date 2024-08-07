package com.example.RestaurantService.controllers;

import com.example.RestaurantService.model.Restaurant;
import com.example.RestaurantService.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;


    @GetMapping()
    public List<Restaurant> getMany(){
        return restaurantService.getRestaurants();
    }

    @GetMapping("/{id}")
    public Restaurant getOne(@PathVariable UUID id){
        return restaurantService.getRestaurant(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody() Restaurant restaurant){
        restaurantService.createRestaurant(restaurant);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable() UUID id, @RequestBody Restaurant restaurant) {
        restaurantService.updateRestaurant(id, restaurant);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable() UUID id){
        restaurantService.deleteRestaurant(id);
    }

}
