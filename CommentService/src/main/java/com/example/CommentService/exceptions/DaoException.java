package com.example.CommentService.exceptions;

import org.springframework.dao.DataAccessException;

public class DaoException extends DataAccessException {
    public DaoException(String e) {
        super(e);
    }
}
