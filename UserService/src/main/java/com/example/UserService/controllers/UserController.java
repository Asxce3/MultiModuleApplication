package com.example.UserService.controllers;

import com.example.UserService.DTO.UserDTO;
import com.example.UserService.DTO.transfer.New;
import com.example.UserService.DTO.transfer.Update;
import com.example.UserService.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping()
    public List<UserDTO> getMany(){
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public UserDTO getOne(@PathVariable UUID id) {
        return userService.getUser(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Validated(New.class) @RequestBody() UserDTO dto) {
        userService.createUser(dto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable UUID id, @Validated({Update.class}) @RequestBody UserDTO dto){
        userService.updateUser(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable() UUID id ) {
        userService.deleteUser(id);
    }

}



