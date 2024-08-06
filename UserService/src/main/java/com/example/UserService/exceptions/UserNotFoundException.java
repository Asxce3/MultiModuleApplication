package com.example.UserService.exceptions;

import java.util.NoSuchElementException;

public class UserNotFoundException extends NoSuchElementException {
    public UserNotFoundException(String e) {
        super(e);
    }
}
