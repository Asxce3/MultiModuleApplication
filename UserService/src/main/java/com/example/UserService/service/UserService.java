package com.example.UserService.service;

import com.example.UserService.DAO.postgres.UserDAOImpl;
import com.example.UserService.exceptions.UserNotFoundException;
import com.example.UserService.service.userUtils.UserUtils;
import com.example.UserService.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Component
public class UserService {
    @Autowired
    UserDAOImpl userDAO;

    @Autowired
    UserUtils userUtils;


    public List<User> getUsers() {
        return userDAO.getMany();
    }

    public User getUser(UUID id) {

        Optional<User> user = userDAO.getOne(id);
        if(user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        return user.get();

    }

    public void createUser(User user) {
        if (userUtils.validateUser(user)) {
            userUtils.setTelephoneUser(user);
        }
        userDAO.create(user);

    }

    public void updateUser(UUID id, User user) {
        userUtils.setTelephoneUser(user);
        userDAO.update(id, user);
    }

    public void deleteUser(UUID id) {
        userDAO.delete(id);
    }

}




