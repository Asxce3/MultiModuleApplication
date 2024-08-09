package org.example.authservice.controllers;

import org.example.authservice.models.Candidate;
import org.example.authservice.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
public class AuthController {
    @Autowired
    Service service;

    @PostMapping()
    public String createToken(@RequestBody Candidate candidate) {
       return service.createToken(candidate);
    }

    @GetMapping()
    public String verifyToken(@RequestHeader String token ) {
        return service.verifyToken(token);
    }
}
