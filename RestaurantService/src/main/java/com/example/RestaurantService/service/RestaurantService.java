package com.example.RestaurantService.service;

import com.example.RestaurantService.DAO.postgres.RestaurantDAOImpl;
import com.example.RestaurantService.exceptions.RestaurantNotFoundException;
import com.example.RestaurantService.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Component
public class RestaurantService {

    @Autowired
    private RestaurantDAOImpl restaurantDAO;

    public List<Restaurant> getRestaurants() {
        return restaurantDAO.getMany();
    }

    public Restaurant getRestaurant(UUID id) {
        Optional<Restaurant> restaurant = restaurantDAO.getOne(id);
        if (restaurant.isEmpty()) {
            throw new RestaurantNotFoundException("Restaurant not found");
        }

        return restaurant.get();
    }

    public void createRestaurant(Restaurant restaurant) {
        restaurantDAO.create(restaurant);
    }

    public void updateRestaurant(UUID id, Restaurant restaurant) {
        restaurantDAO.update(id, restaurant);
    }

    public void deleteRestaurant(UUID id) {
         restaurantDAO.delete(id);
    }

}
