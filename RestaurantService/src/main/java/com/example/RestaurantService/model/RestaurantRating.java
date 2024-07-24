package com.example.RestaurantService.model;

import java.util.UUID;

public class RestaurantRating extends Restaurant{
    private UUID id;
    private double rating;
    private int countRatings;
    private UUID restaurantId;

    public RestaurantRating(double rating, int countOfRatings) {
        this.rating = rating;
        this.countRatings = countOfRatings;
    }

    public RestaurantRating(){}

    public String toString() {
        return "id : " + id + "\nrating : " + rating + "\ncountRatings : " + countRatings;
    }


    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getCountRatings() {
        return countRatings;
    }

    public void setCountRatings(int countRatings) {
        this.countRatings = countRatings;
    }


    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }
}
