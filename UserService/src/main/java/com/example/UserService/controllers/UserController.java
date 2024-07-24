package com.example.UserService.controllers;

import com.example.UserService.model.UserEdit;
import com.example.UserService.service.UserService;

import com.example.UserService.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<?> getMany(){
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable UUID id){
        return userService.getUser(id);
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody() User user ){
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody UserEdit user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable() UUID id ){
        return userService.deleteUser(id);
    }

}



