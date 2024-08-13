package com.example.UserService.controllerAdvices;

import com.example.UserService.DTO.Response;
import com.example.UserService.exceptions.CountryCodeException;
import com.example.UserService.exceptions.UserNotFoundException;
import com.example.UserService.exceptions.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Advice {

    private final Logger logger = LoggerFactory.getLogger(Advice.class);
    private final String logMessage = "message: {}, HTTP status: {}";

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Response> userNotFoundException(UserNotFoundException e) {
        Response response = new Response(e.getMessage());
        logger.warn(logMessage, response.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DaoException.class)
    public ResponseEntity<Response> daoException(DaoException e) {
        Response response = new Response(e.getMessage());
        logger.warn(logMessage, response.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<Response> duplicateException(DuplicateKeyException e) {
        Response response = new Response(e.getMessage());
        logger.warn(logMessage, response.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Response> dataIntegrityViolation(DataIntegrityViolationException e) {
        Response response = new Response(e.getMessage());
        logger.warn(logMessage, response.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CountryCodeException.class)
    public ResponseEntity<Response> dataIntegrityViolation(CountryCodeException e) {
        Response response = new Response(e.getMessage());
        logger.warn(logMessage, response.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
