package com.example.CommentService.controllerAdvices;

import com.example.CommentService.dto.Response;
import com.example.CommentService.exceptions.CommentNotFoundException;
import com.example.CommentService.exceptions.DaoException;
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

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<Response> commentNotFoundException(CommentNotFoundException e) {
        Response response = new Response(e.getMessage());
        logger.warn(logMessage, response.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DaoException.class)
    public ResponseEntity<Response> daoException(DaoException e) {
        Response response = new Response(e.getMessage());
        logger.warn(logMessage, response.getMessage(), HttpStatus.BAD_REQUEST);
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

}
