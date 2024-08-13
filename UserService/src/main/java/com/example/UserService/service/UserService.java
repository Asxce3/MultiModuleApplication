package com.example.UserService.service;

import com.example.UserService.DAO.postgres.UserDAOImpl;
import com.example.UserService.DTO.UserDTO;
import com.example.UserService.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Component
public class UserService {
    @Autowired
    UserDAOImpl userDAO;


    public List<UserDTO> getUsers() {
        return userDAO.getMany();
    }

    public UserDTO getUser(UUID id) {

        Optional<UserDTO> user = userDAO.getOne(id);
        if(user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        return user.get();

    }

    public void createUser(UserDTO dto) {
        userDAO.create(dto);

    }

    public void updateUser(UUID id, UserDTO dto) {
        userDAO.update(id, dto);
    }

    public void deleteUser(UUID id) {
        userDAO.delete(id);
    }

}




