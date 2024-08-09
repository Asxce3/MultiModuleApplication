package org.example.authservice.exceptions;

import org.springframework.dao.DataAccessException;

public class DaoException extends DataAccessException {
    public DaoException(String message) {
        super(message);
    }
}
