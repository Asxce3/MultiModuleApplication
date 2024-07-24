package com.example.RestaurantService.repository;

import com.example.RestaurantService.model.Restaurant;
import com.example.RestaurantService.repository.DAO.postgres.RestaurantDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class RestaurantRepository {
    @Autowired
    private RestaurantDAOImpl restaurantDAO;

    public ResponseEntity<?> getRestaurants(){
        try {
            List<Restaurant> restaurants = restaurantDAO.getMany();
            return ResponseEntity.status(200).body(restaurants);

        } catch(CannotGetJdbcConnectionException e) {

            e.printStackTrace();
            return ResponseEntity.status(500).body(e.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    public ResponseEntity<?> getRestaurant(UUID id) {
        try {
            Optional<Restaurant> optRestaurant = restaurantDAO.getOne(id);
            return ResponseEntity.status(200).body(optRestaurant.get());

        } catch(CannotGetJdbcConnectionException e) {

            e.printStackTrace();
            return ResponseEntity.status(500).body(e.getMessage());

        }  catch (Exception e) {

            e.printStackTrace();
            return ResponseEntity.status(404).body(e.getMessage());
        }

    }

    public ResponseEntity<?> createRestaurant(Restaurant restaurant) {
        try {
            restaurantDAO.create(restaurant);
            return ResponseEntity.status(201).build();

        } catch(CannotGetJdbcConnectionException e) {

            e.printStackTrace();
            return ResponseEntity.status(500).body(e.getMessage());

        }  catch (Exception e) {

            e.printStackTrace();
            return ResponseEntity.status(400).body(e.getMessage());
        }

    }

    public ResponseEntity<?> updateRestaurant(UUID id, Restaurant restaurant) {
        try {
            restaurantDAO.update(id, restaurant);
            return ResponseEntity.status(200).build();

        } catch(CannotGetJdbcConnectionException e) {

            e.printStackTrace();
            return ResponseEntity.status(500).body(e.getMessage());

        }  catch (Exception e) {

            e.printStackTrace();
            return ResponseEntity.status(404).body(e.getMessage());
        }

    }

    public ResponseEntity<?> deleteRestaurant(UUID id) {
        try {
            restaurantDAO.delete(id);
            return ResponseEntity.status(200).build();

        } catch(CannotGetJdbcConnectionException e) {

            e.printStackTrace();
            return ResponseEntity.status(500).body(e.getMessage());

        }  catch (Exception e) {

            e.printStackTrace();
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }


}


