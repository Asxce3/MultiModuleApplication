package org.example.authservice.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.example.authservice.models.Candidate;
import org.example.authservice.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping()
public class AuthController {
    @Autowired
    Service service;

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public void createToken(@RequestBody Candidate candidate, HttpServletResponse res) {
       HashMap<String,String> map = service.createToken(candidate);

       Cookie cookie1 = new Cookie("accessToken", map.get("accessToken"));
       Cookie cookie2 = new Cookie("refreshToken", map.get("refreshToken"));
       cookie1.setMaxAge(60);
       cookie2.setMaxAge(120);

       res.addCookie(cookie1);
       res.addCookie(cookie2);
       res.setContentType("text/plain");

    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public void getCookie(@CookieValue(value = "accessToken") String token) {
        service.verifyToken(token);
    }

    @GetMapping("/refresh")
    @ResponseStatus(HttpStatus.OK)
    public String refreshToken(@CookieValue(value = "refreshToken") String token, HttpServletResponse res) {
        String accessToken = service.refreshToken(token);
        Cookie cookie = new Cookie("accessToken", accessToken);
        cookie.setMaxAge(60);
        res.addCookie(cookie);
        res.setContentType("text/plain");
        return "Refresh Token success";

    }
}
