package org.example.authservice.controllers;

import org.example.authservice.models.Candidate;
import org.example.authservice.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
public class AuthController {
    @Autowired
    Service authService;

    @PostMapping()
    public ResponseEntity<?> checkUser(@RequestBody Candidate candidate) {
       return authService.checkUser(candidate);
    }

    @GetMapping()
    public ResponseEntity<?> checkToken(@RequestHeader String token ) {
        return authService.checkJWT(token);
    }
}
