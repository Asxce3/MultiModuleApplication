package com.example.UserService.service;

import com.example.UserService.repository.UserRepository;
import com.example.UserService.service.userUtils.UserUtils;
import com.example.UserService.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
public class UserService {
    @Autowired
    UserRepository repository;

    @Autowired
    UserUtils userUtils;


    public ResponseEntity<?> getUsers() {
        return repository.getUsers();
    }

    public ResponseEntity<?> getUser(UUID id) {
        return repository.getUser(id);

    }

    public ResponseEntity<?> createUser(User user) {
        if (userUtils.validateUser(user)) {

            userUtils.setTelephoneUser(user);
            return repository.createUser(user);
        } else {
            return ResponseEntity.badRequest().body("Данные введены не корректно");
        }
    }

    public ResponseEntity<?> updateUser(UUID id, User user) {
        System.out.println(user);
        try {
            HttpStatusCode code = getUser(id).getStatusCode();
            if (code.is4xxClientError()) {
                return ResponseEntity.badRequest().body("Такой пользователь не существует");
            }
            if (userUtils.validateSocietyFields(user)) {
                userUtils.setTelephoneUser(user);
                return repository.updateUser(id, user);

            }
            return ResponseEntity.badRequest().body("Данные введены не корректно");

        }   catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("хз что случилось");
        }

    }

    public ResponseEntity<?> deleteUser(UUID id) {

            ResponseEntity<?> getUser = getUser(id);

            if (getUser.getStatusCode().is4xxClientError()) {
                return getUser;
            }

            return repository.deleteUser(id);
    }

}




