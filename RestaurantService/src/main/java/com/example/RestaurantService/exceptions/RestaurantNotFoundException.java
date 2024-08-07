package com.example.RestaurantService.exceptions;

import java.util.NoSuchElementException;

public class RestaurantNotFoundException extends NoSuchElementException {
    public RestaurantNotFoundException(String e) {
        super(e);
    }
}
