package com.example.RestaurantService.model;


import java.util.UUID;

public class Restaurant {
    private UUID id;
    private String name;
    private double rating;
    private int countRatings;

    public Restaurant(String name) {
        this.name = name;
    }

    public Restaurant() {
    }

    public String toString() {
        return "id : " + id + "\nname : " + name ;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
