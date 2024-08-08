package com.example.UserService.controllers;

import com.example.UserService.model.UserEdit;
import com.example.UserService.service.UserService;
import com.example.UserService.model.User;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping()
    public List<User> getMany(HttpServletRequest request){

        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getOne(@PathVariable UUID id) {
        return userService.getUser(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody() User user ) {
        userService.createUser(user);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable UUID id, @RequestBody UserEdit user){
        userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable() UUID id ) {
        userService.deleteUser(id);
    }

}



