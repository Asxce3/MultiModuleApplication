package org.example.authservice.controllerAdvices;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.example.authservice.DTO.Response;
import org.example.authservice.exceptions.DaoException;
import org.example.authservice.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class Advice {

    private final Logger logger = LoggerFactory.getLogger(Advice.class);
    private final String logMessage = "message: {}, HTTP status: {}";

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Response> userNotFoundException(UserNotFoundException e) {
        Response response = new Response(e.getMessage());
        logger.error(logMessage, response.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DaoException.class)
    public ResponseEntity<Response> DaoException(DaoException e) {
        Response response = new Response(e.getMessage());
        logger.error(logMessage, response.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(JWTCreationException.class)
    public ResponseEntity<Response> JWTCreationException(JWTCreationException e) {
        Response response = new Response(e.getMessage());
        logger.error(logMessage, response.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<Response> JWTVerificationException(JWTVerificationException e) {
        Response response = new Response(e.getMessage());
        logger.error(logMessage, response.getMessage(), HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }



}
