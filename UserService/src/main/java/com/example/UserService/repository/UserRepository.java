package com.example.UserService.repository;

import com.example.UserService.model.User;
import com.example.UserService.repository.DAO.postgres.UserDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Component
public class UserRepository {
    @Autowired
    UserDAOImpl userDAO;

    public ResponseEntity<?> getUsers(){
        try {
            List<User> users = userDAO.getMany();
            return ResponseEntity.status(200).body(users);

        } catch(CannotGetJdbcConnectionException e) {

            e.printStackTrace();
            return ResponseEntity.status(500).body(e.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    public ResponseEntity<?> getUser(UUID id) {
        try {
            Optional<User> user = userDAO.getOne(id);
            return ResponseEntity.status(200).body(user.get());

        } catch(CannotGetJdbcConnectionException e) {

            e.printStackTrace();
            return ResponseEntity.status(500).body(e.getMessage());

        }  catch (Exception e) {

            e.printStackTrace();
            return ResponseEntity.status(404).body(e.getMessage());
        }

    }

    public ResponseEntity<?> createUser(User user) {
        try {
            userDAO.create(user);
            return ResponseEntity.status(201).build();

        } catch(CannotGetJdbcConnectionException e) {

            e.printStackTrace();
            return ResponseEntity.status(500).body(e.getMessage());

        }  catch (Exception e) {

            e.printStackTrace();
            return ResponseEntity.status(400).body(e.getMessage());
        }

    }

    public ResponseEntity<?> updateUser(UUID id, User user) {
        try {
            userDAO.update(id, user);
            return ResponseEntity.status(200).build();

        } catch(CannotGetJdbcConnectionException e) {

            e.printStackTrace();
            return ResponseEntity.status(500).body(e.getMessage());

        }  catch (Exception e) {

            e.printStackTrace();
            return ResponseEntity.status(404).body(e.getMessage());
        }

    }

    public ResponseEntity<?> deleteUser(UUID id) {
        try {
            userDAO.delete(id);
            return ResponseEntity.status(200).build();

        } catch(CannotGetJdbcConnectionException e) {

            e.printStackTrace();
            return ResponseEntity.status(500).body(e.getMessage());

        }  catch (Exception e) {

            e.printStackTrace();
            return ResponseEntity.status(404).body(e.getMessage());
        }

    }
}
