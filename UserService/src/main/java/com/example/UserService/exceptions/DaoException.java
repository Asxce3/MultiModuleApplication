package com.example.UserService.exceptions;

import org.springframework.dao.DataAccessException;

public class DaoException extends DataAccessException {
    public DaoException(String e) {
        super(e);
    }
}
