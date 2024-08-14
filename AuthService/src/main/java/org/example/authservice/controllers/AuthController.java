package org.example.authservice.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.example.authservice.models.Candidate;
import org.example.authservice.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping()
public class AuthController {
    @Autowired
    Service service;

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public void createToken(@RequestBody Candidate candidate, HttpServletResponse response) {
        UUID userId = service.checkUser(candidate);
        HashMap<String, String> tokens = service.createTokens(userId);
        service.setCookieTokens(tokens, response);

    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public void verifyToken(@CookieValue(value = "accessToken") String token) {
        service.verifyAccessToken(token);
    }

    @GetMapping("/refresh")
    @ResponseStatus(HttpStatus.OK)
    public void refreshToken(@CookieValue(value = "refreshToken") String refreshToken, HttpServletResponse response) {
        UUID userId = service.changeTokens(refreshToken);
        HashMap<String, String> tokens = service.createTokens(userId);
        service.setCookieTokens(tokens, response);

    }
}
